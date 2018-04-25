package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import uj.jwzp.w2.e3.logic.writer.BulkFilesWriter;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;
import uj.jwzp.w2.e3.logic.writer.YAMLTransactionWriter;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

import static org.mockito.ArgumentMatchers.anyInt;

public class YAMLTransactionWriterTest {
    private final static int TEST_TRANSACTION_ID = 1;
    private final static ZonedDateTime TEST_TRANSACTION_TIMESTAMP =
            ZonedDateTime.parse("2018-03-08T12:31:13.000-0100", Transaction.DATE_TIME_FORMATTER);
    private final static int TEST_TRANSACTION_CUSTOMER_ID = 12;
    private final static Item TEST_PRODUCT1 = new Item("bułeczka", BigDecimal.valueOf(1.2));
    private final static Item TEST_PRODUCT2 = new Item("mleko 3% 1l", BigDecimal.valueOf(2.3));
    private final static LinkedHashMap<Item, Integer> TEST_TRANSACTION_ITEMS_ORDERED;
    private final static BigDecimal TEST_TRANSACTION_SUM = BigDecimal.valueOf(5.9);

    private final static TransactionWriter.AllowedOutputFormats format = TransactionWriter.AllowedOutputFormats.yaml;

    static {
        TEST_TRANSACTION_ITEMS_ORDERED = new LinkedHashMap<Item, Integer>();
        TEST_TRANSACTION_ITEMS_ORDERED.put(TEST_PRODUCT1, 3);
        TEST_TRANSACTION_ITEMS_ORDERED.put(TEST_PRODUCT2, 1);
    }

    private final static String TEST_TRANSACTION_EXPECTED_YAML_OUTPUT =
            "transaction:" +
                    "\n\tid: 1" +
                    "\n\ttimestamp: 2018-03-08T12:31:13.000-0100" +
                    "\n\tcustomer_id: 12" +
                    "\n\titems:" +
                        "\n\t\t- name: bułeczka" +
                        "\n\t\t  quantity: 3" +
                        "\n\t\t  price: 1.2\n" +

                        "\n\t\t- name: mleko 3% 1l" +
                        "\n\t\t  quantity: 1" +
                        "\n\t\t  price: 2.3\n" +
                    "\n\tsum: 5.9";

    @Test
    public void shouldStringifyToJSON() throws IOException {
        Transaction transaction = Mockito.mock(Transaction.class);
        BulkFilesWriter writer = Mockito.mock(BulkFilesWriter.class);
        YAMLTransactionWriter uut = new YAMLTransactionWriter(writer);

        Mockito.when(transaction.getId()).thenReturn(TEST_TRANSACTION_ID);
        Mockito.when(transaction.getCustomerId()).thenReturn(TEST_TRANSACTION_CUSTOMER_ID);
        Mockito.when(transaction.getItemsOrdered()).thenReturn(TEST_TRANSACTION_ITEMS_ORDERED);
        Mockito.when(transaction.getTimestamp()).thenReturn(TEST_TRANSACTION_TIMESTAMP);
        Mockito.when(transaction.getSum()).thenReturn(TEST_TRANSACTION_SUM);

        Assert.assertEquals(TEST_TRANSACTION_EXPECTED_YAML_OUTPUT, uut.parseTransaction(transaction));
    }

    @Test
    public void shouldWriteToFile() throws IOException {
        Transaction transaction = Mockito.mock(Transaction.class);
        BulkFilesWriter writer = Mockito.mock(BulkFilesWriter.class);
        FileOutputStream fileMock = Mockito.mock(FileOutputStream.class);
        YAMLTransactionWriter uut = new YAMLTransactionWriter(writer);

        Mockito.when(writer.getWriter(anyInt())).thenAnswer((a) -> fileMock);
        Mockito.when(writer.getFormat()).thenReturn("json");

        Mockito.when(transaction.getId()).thenReturn(TEST_TRANSACTION_ID);
        Mockito.when(transaction.getCustomerId()).thenReturn(TEST_TRANSACTION_CUSTOMER_ID);
        Mockito.when(transaction.getItemsOrdered()).thenReturn(TEST_TRANSACTION_ITEMS_ORDERED);
        Mockito.when(transaction.getTimestamp()).thenReturn(TEST_TRANSACTION_TIMESTAMP);
        Mockito.when(transaction.getSum()).thenReturn(TEST_TRANSACTION_SUM);

        uut.write(transaction);

        Mockito.verify(writer).ensureOutDir();
        Mockito.verify(fileMock, Mockito.times(1))
                .write(Mockito.eq(TEST_TRANSACTION_EXPECTED_YAML_OUTPUT.getBytes()));
    }
}
