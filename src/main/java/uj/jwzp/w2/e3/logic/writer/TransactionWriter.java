package uj.jwzp.w2.e3.logic.writer;

import lombok.extern.slf4j.Slf4j;
import uj.jwzp.w2.e3.model.Transaction;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public abstract class TransactionWriter {
    protected BulkFilesWriter writer;

    public TransactionWriter(BulkFilesWriter writer) {
        this.writer = writer;
    }

    abstract public String parseTransaction(Transaction transaction);

    public void write(Transaction transaction) throws IOException {
        writer.ensureOutDir();
        log.trace("Writing transaction " + transaction.getId()
                + " data to " + writer.getFormat().toUpperCase() + " file.");
        try (FileOutputStream output = writer.getWriter(transaction.getId())) {
            output.write(parseTransaction(transaction).getBytes());
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    public enum AllowedOutputFormats { json, xml, yaml }
}
