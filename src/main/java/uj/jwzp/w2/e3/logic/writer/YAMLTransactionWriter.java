package uj.jwzp.w2.e3.logic.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;

import java.util.Map;

import static uj.jwzp.w2.e3.model.Transaction.DATE_TIME_FORMATTER;

@Slf4j
@Lazy
@Service("yaml")
public class YAMLTransactionWriter extends TransactionWriter {
    @Autowired
    public YAMLTransactionWriter(BulkFilesWriter writer) {
        super(writer);
    }

    public String parseTransaction(Transaction transaction) {
        StringBuilder sb = new StringBuilder("transaction:\n\tid: ");
        sb.append(transaction.getId());
        sb.append("\n\ttimestamp: ");
        sb.append(transaction.getTimestamp().format(DATE_TIME_FORMATTER));
        sb.append("\n\tcustomer_id: ");
        sb.append(transaction.getCustomerId());
        sb.append("\n\titems:");
        for (Map.Entry<Item, Integer> entry : transaction.getItemsOrdered().entrySet()) {
            sb.append("\n\t\t- name: ");
            sb.append(entry.getKey().getName());
            sb.append("\n\t\t  quantity: ");
            sb.append(entry.getValue());
            sb.append("\n\t\t  price: ");
            sb.append(entry.getKey().getPrice());
            sb.append("\n");
        }
        sb.append("\n\tsum: ");
        sb.append(transaction.getSum());
        return sb.toString();
    }
}
