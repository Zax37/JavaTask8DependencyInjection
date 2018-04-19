package uj.jwzp.w2.e3.logic.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uj.jwzp.w2.e3.model.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Qualifier("csv")
public class CSVItemsReader implements ItemsReader {
    private static final String[] expectedColumns = { "name", "price" };

    private BufferedReader input;

    @Autowired
    public CSVItemsReader(BufferedReader input) {
        this.input = input;
    }

    public List<Item> read() throws IOException {
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
    }

    @Override
    public void close() throws IOException {
        input.close();
    }
}
