package uj.jwzp.w2.e3.logic.beans;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;

@Lazy
@Service
public class TransactionWriterSupplier {
    private BeanFactory beans;

    @Autowired
    TransactionWriterSupplier(BeanFactory beans) {
        this.beans = beans;
    }

    @Bean
    @Primary
    public TransactionWriter getCorrectTransactionWriter(@Value("${format}") String format) {
        return beans.getBean(format, TransactionWriter.class);
    }
}
