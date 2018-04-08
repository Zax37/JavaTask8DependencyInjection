package uj.jwzp.w2.e3.logic.writer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import uj.jwzp.w2.e3.model.Transaction;

import java.io.IOException;

@Configuration
public interface TransactionWriter {
    void write(Transaction transaction) throws IOException;

    enum AllowedOutputFormats { json, xml, yaml }
}
