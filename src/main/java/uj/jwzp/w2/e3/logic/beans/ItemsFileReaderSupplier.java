package uj.jwzp.w2.e3.logic.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Slf4j
@Lazy
@Configuration
public class ItemsFileReaderSupplier {
    @Bean
    BufferedReader getItemsFileReader(@Value("${itemsFile}") Path itemsFile) throws FileNotFoundException {
        return new BufferedReader(new FileReader(itemsFile.toString()));
    }
}
