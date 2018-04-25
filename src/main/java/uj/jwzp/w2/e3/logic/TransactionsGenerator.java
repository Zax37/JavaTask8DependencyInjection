package uj.jwzp.w2.e3.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.logic.reader.ItemsReader;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;
import uj.jwzp.w2.e3.model.TransactionGeneratorArgs;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Lazy
@Service
public class TransactionsGenerator {
    private final ItemsReader reader;
    private final TransactionWriter writer;
    private final TransactionGeneratorArgs args;

    @Autowired
    public TransactionsGenerator(
        ItemsReader reader,
        TransactionWriter writer,
        TransactionGeneratorArgs args
    ) {
        this.reader = reader;
        this.writer = writer;
        this.args = args;
    }

    public Transaction getSingleRandomTransaction(List<Item> availableItems, int id) {
        Collections.shuffle(availableItems);
        int count = args.getItemsCount().random();
        LinkedHashMap<Item, Integer> orderedItems = new LinkedHashMap<>();
        for (int j = 0; j < count; j++) {
            orderedItems.put(availableItems.get(j%availableItems.size()), args.getItemsQuantity().random());
        }

        return Transaction.builder()
                .id(id)
                .timestamp(args.getDateRange().random())
                .customerId(args.getCustomerIds().random())
                .itemsOrdered(orderedItems)
                .build();
    }

    public void generate() throws IOException {
        try {
            List<Item> availableItems = reader.read();
            for (int i = 1; i <= args.getEventsCount(); i++) {
                log.trace("Generating transaction " + i);
                Transaction transaction = getSingleRandomTransaction(availableItems, i);
                writer.write(transaction);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }

        log.info("Successfully generated and saved " + args.getEventsCount() + " transactions.");
    }
}
