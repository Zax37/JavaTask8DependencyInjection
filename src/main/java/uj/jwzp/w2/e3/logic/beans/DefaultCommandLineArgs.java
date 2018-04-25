package uj.jwzp.w2.e3.logic.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Configuration
public class DefaultCommandLineArgs {
    @Bean
    public IntRangeProperty getDefaultCustomerIds() {
        return new IntRangeProperty(1, 20);
    }

    @Bean
    public DateRangeProperty getDefaultDateRange() {
        ZonedDateTime today = ZonedDateTime.now();
        return new DateRangeProperty(
                today.with(LocalTime.MIN),
                today.with(LocalTime.MAX));
    }

    @Bean
    public Path getDefaultItemsFile() {
        return Paths.get("items.csv");
    }

    @Bean
    public IntRangeProperty getDefaultItemsCount() {
        return new IntRangeProperty(1, 5);
    }

    @Bean
    public IntRangeProperty getDefaultItemsQuantity() {
        return new IntRangeProperty(1, 5);
    }

    @Bean
    public Integer getDefaultEventsCount() {
        return 100;
    }

    @Bean
    public Path getDefaultOutDir() {
        return Paths.get("./");
    }

    @Bean
    public TransactionWriter.AllowedOutputFormats getDefaultOutFormat() {
        return TransactionWriter.AllowedOutputFormats.json;
    }
}
