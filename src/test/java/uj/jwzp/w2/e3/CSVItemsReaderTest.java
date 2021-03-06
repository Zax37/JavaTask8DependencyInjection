package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import uj.jwzp.w2.e3.logic.reader.CSVItemsReader;
import uj.jwzp.w2.e3.model.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CSVItemsReaderTest {
    private final static String INVALID_CSV_HEADER1 = "invalid";
    private final static String INVALID_CSV_HEADER2 = "name,invalid";
    private final static String INVALID_CSV_PRODUCT1 = "testName";
    private final static String CORRECT_CSV_HEADER = "name,price";
    private final static String CORRECT_CSV_PRODUCT1 = "\"testName\",1.37";
    private final static String TEST_PRODUCT1_NAME = "testName";
    private final static BigDecimal TEST_PRODUCT1_PRICE = BigDecimal.valueOf(1.37);
    private final static int TEST_PRODUCTS_COUNT = 1;

    @Test
    public void shouldReadCorrectCSV() {
        BufferedReader mockedReader = Mockito.mock(BufferedReader.class);
        try (CSVItemsReader uut = new CSVItemsReader(mockedReader)){
            Mockito.when(mockedReader.readLine())
                    .thenReturn(CORRECT_CSV_HEADER)
                    .thenReturn(CORRECT_CSV_PRODUCT1)
                    .thenReturn(null);

            List<Item> list = uut.read();

            Assert.assertEquals(TEST_PRODUCTS_COUNT, list.size());
            Item product1 = list.get(0);
            Assert.assertEquals(TEST_PRODUCT1_NAME, product1.getName());
            Assert.assertEquals(TEST_PRODUCT1_PRICE, product1.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionWhenCSVIncorrect1() throws IOException {
        BufferedReader mockedReader = Mockito.mock(BufferedReader.class);

        Mockito.when(mockedReader.readLine())
                .thenReturn(INVALID_CSV_HEADER1)
                .thenReturn(null);

        CSVItemsReader uut = new CSVItemsReader(mockedReader);
        List<Item> list = uut.read();
        uut.close();
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionWhenCSVIncorrect2() throws IOException {
        BufferedReader mockedReader = Mockito.mock(BufferedReader.class);

        Mockito.when(mockedReader.readLine())
                .thenReturn(INVALID_CSV_HEADER2)
                .thenReturn(null);

        CSVItemsReader uut = new CSVItemsReader(mockedReader);
        List<Item> list = uut.read();
        uut.close();
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionWhenCSVIncorrect3() throws IOException {
        BufferedReader mockedReader = Mockito.mock(BufferedReader.class);

        Mockito.when(mockedReader.readLine())
                .thenReturn(CORRECT_CSV_HEADER)
                .thenReturn(INVALID_CSV_PRODUCT1)
                .thenReturn(null);

        CSVItemsReader uut = new CSVItemsReader(mockedReader);
        List<Item> list = uut.read();
        uut.close();
    }
}
