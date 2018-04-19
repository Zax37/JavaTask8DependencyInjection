package uj.jwzp.w2.e3.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Configuration
public class SourceBean {
    @Bean
    BufferedReader getItemsFileReader(@Value("${itemsFile}") Path itemsFile) throws FileNotFoundException {
        return new BufferedReader(new FileReader(itemsFile.toString()));
    }
}
