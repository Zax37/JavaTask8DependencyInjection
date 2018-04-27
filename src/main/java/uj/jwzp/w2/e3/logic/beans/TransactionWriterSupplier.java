package uj.jwzp.w2.e3.logic.beans;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.logic.writer.JSONTransactionWriter;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;
import uj.jwzp.w2.e3.logic.writer.XMLTransactionWriter;
import uj.jwzp.w2.e3.logic.writer.YAMLTransactionWriter;

@Lazy
@Service
public class TransactionWriterSupplier {
    private JSONTransactionWriter jsonWriter;
    private XMLTransactionWriter xmlWriter;
    private YAMLTransactionWriter yamlWriter;

    @Autowired
    TransactionWriterSupplier(
            JSONTransactionWriter jsonWriter,
            XMLTransactionWriter xmlWriter,
            YAMLTransactionWriter yamlWriter
    ) {
        this.jsonWriter = jsonWriter;
        this.xmlWriter = xmlWriter;
        this.yamlWriter = yamlWriter;
    }

    @Bean
    @Primary
    public TransactionWriter getCorrectTransactionWriter(
            @Value("${format}") TransactionWriter.AllowedOutputFormats format
    ) {
        switch (format) {
            case json:
            default:
                return jsonWriter;
            case xml:
                return xmlWriter;
            case yaml:
                return yamlWriter;
        }
    }
}
