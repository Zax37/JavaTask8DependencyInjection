package uj.jwzp.w2.e3.logic.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.logic.condition.JSONFormatCondition;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static uj.jwzp.w2.e3.model.Transaction.DATE_TIME_FORMATTER;

@Slf4j
@Service
@Lazy
@Conditional(JSONFormatCondition.class)
public class JSONTransactionWriter implements TransactionWriter {
    private Path outDir;

    public JSONTransactionWriter(@Value("${outDir}") Path outDir) throws IOException {
        log.trace("Creating output directory if it does not exist.");
        Files.createDirectories(outDir);
        this.outDir = outDir;
    }

    public String parseTransaction(Transaction transaction) {
        StringBuilder sb = new StringBuilder("{\n  \"id\": ");
        sb.append(transaction.getId());
        sb.append(",\n  \"timestamp\": \"");
        sb.append(transaction.getTimestamp().format(DATE_TIME_FORMATTER));
        sb.append("\",\n  \"customer_id\": ");
        sb.append(transaction.getCustomerId());
        sb.append(",\n  \"items\": [");
        boolean first = true;
        for (Map.Entry<Item, Integer> entry : transaction.getItemsOrdered().entrySet()) {
            sb.append(first?"\n    {":",\n    {");
            sb.append("\n      \"name\": \"");
            sb.append(entry.getKey().getName());
            sb.append("\",\n      \"quantity\": ");
            sb.append(entry.getValue());
            sb.append(",\n      \"price\": ");
            sb.append(entry.getKey().getPrice());
            sb.append("\n    }");
            first = false;
        }
        sb.append("\n  ],\n  \"sum\": ");
        sb.append(transaction.getSum());
        sb.append("\n}");
        return sb.toString();
    }

    @Override
    public void write(Transaction transaction) throws IOException {
        log.trace("Writing transaction " + transaction.getId() + " data to JSON file.");
        try (FileOutputStream output =
                     new FileOutputStream(outDir.resolve(transaction.getId()+".json").toFile())) {
            output.write(parseTransaction(transaction).getBytes());
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }
}
