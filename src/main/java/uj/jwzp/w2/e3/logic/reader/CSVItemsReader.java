package uj.jwzp.w2.e3.logic.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.model.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Qualifier("csv")
public class CSVItemsReader implements ItemsReader {
    private static final String[] expectedColumns = { "name", "price" };

    @Value("${itemsFile}")
    Path itemsFile;

    public List<Item> read() throws IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(itemsFile.toFile()))) {
            List<Item> ret = new ArrayList<>();
            String headerLine = input.readLine();
            String[] columns = headerLine.split(",");

            if (columns.length != expectedColumns.length) {
                throw new IOException("Unknown file format.");
            }

            for (int i = 0; i < expectedColumns.length; i++) {
                if (!columns[i].equals(expectedColumns[i])) {
                    throw new IOException("Unknown file format.");
                }
            }

            String line;
            while ((line = input.readLine()) != null) {
                columns = line.split(",");

                if(columns.length != expectedColumns.length) {
                    throw new IOException("Unknown file format.");
                }

                String name = columns[0];
                name = name.substring(1, name.length()-1);
                Double price = Double.parseDouble(columns[1]);
                ret.add(new Item(name, BigDecimal.valueOf(price)));
            }
            return ret;
        } catch (IOException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }
}
