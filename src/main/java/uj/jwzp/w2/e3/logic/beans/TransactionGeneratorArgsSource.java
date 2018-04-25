package uj.jwzp.w2.e3.logic.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import uj.jwzp.w2.e3.model.TransactionGeneratorArgs;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

@Lazy
@Configuration
public class TransactionGeneratorArgsSource {
    @Bean
    public TransactionGeneratorArgs getTransactionGeneratorArgs(
            @Value("${customerIds}") IntRangeProperty customerIds,
            @Value("${dateRange}") DateRangeProperty dateRange,
            @Value("${itemsCount}") IntRangeProperty itemsCount,
            @Value("${itemsQuantity}") IntRangeProperty itemsQuantity,
            @Value("${eventsCount}") Integer eventsCount
    ) {
        return new TransactionGeneratorArgs(customerIds, dateRange, itemsCount, itemsQuantity, eventsCount);
    }
}
