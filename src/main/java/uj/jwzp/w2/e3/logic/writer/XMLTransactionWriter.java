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
@Service("xml")
public class XMLTransactionWriter extends TransactionWriter {
    @Autowired
    public XMLTransactionWriter(BulkFilesWriter writer) {
        super(writer);
    }

    public String parseTransaction(Transaction transaction) {
        StringBuilder sb = new StringBuilder("<transaction id=\"");
        sb.append(transaction.getId());
        sb.append("\" timestamp=\"");
        sb.append(transaction.getTimestamp().format(DATE_TIME_FORMATTER));
        sb.append("\" customer_id=\"");
        sb.append(transaction.getCustomerId());
        sb.append("\" sum=\"");
        sb.append(transaction.getSum());
        sb.append("\">");
        for (Map.Entry<Item, Integer> entry : transaction.getItemsOrdered().entrySet()) {
            sb.append("\n\t<item name=\"");
            sb.append(entry.getKey().getName());
            sb.append("\" quantity=\"");
            sb.append(entry.getValue());
            sb.append("\" price=\"");
            sb.append(entry.getKey().getPrice());
            sb.append("\"/>");
        }
        sb.append("\n</transaction>");
        return sb.toString();
    }
}
