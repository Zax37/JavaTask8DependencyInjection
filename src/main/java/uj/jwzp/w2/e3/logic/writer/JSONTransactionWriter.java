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
@Service("json")
public class JSONTransactionWriter extends TransactionWriter {
    @Autowired
    public JSONTransactionWriter(BulkFilesWriter writer) {
        super(writer);
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
}
