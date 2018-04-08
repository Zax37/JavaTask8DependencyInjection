package uj.jwzp.w2.e3.logic.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.logic.condition.YAMLFormatCondition;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static uj.jwzp.w2.e3.model.Transaction.DATE_TIME_FORMATTER;

@Slf4j
@Lazy
@Service
@Conditional(YAMLFormatCondition.class)
public class YAMLTransactionWriter implements TransactionWriter {
    private Path outDir;

    public YAMLTransactionWriter(@Value("${outDir}") Path outDir) throws IOException {
        log.trace("Creating output directory if it does not exist.");
        Files.createDirectories(outDir);
        this.outDir = outDir;
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
            sb.append("\n\t\tname: ");
            sb.append(entry.getKey().getName());
            sb.append("\n\t\tquantity: ");
            sb.append(entry.getValue());
            sb.append("\n\t\tprice: ");
            sb.append(entry.getKey().getPrice());
        }
        sb.append("\n\tsum: ");
        sb.append(transaction.getSum());
        return sb.toString();
    }

    @Override
    public void write(Transaction transaction) throws IOException {
        log.trace("Writing transaction " + transaction.getId() + " data to YAML file.");
        try (FileOutputStream output =
                     new FileOutputStream(outDir.resolve(transaction.getId()+".yaml").toFile())) {
            output.write(parseTransaction(transaction).getBytes());
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }
}
