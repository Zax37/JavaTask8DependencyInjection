package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class TransactionTest {
    private final static int TEST_TRANSACTION_ID = 1;
    private final static ZonedDateTime TEST_TRANSACTION_TIMESTAMP =
            ZonedDateTime.parse("2018-03-08T12:31:13.000-0100", Transaction.DATE_TIME_FORMATTER);
    private final static int TEST_TRANSACTION_CUSTOMER_ID = 12;
    private final static Item TEST_PRODUCT1 = new Item("bułeczka", BigDecimal.valueOf(1.2));
    private final static Item TEST_PRODUCT2 = new Item("mleko 3% 1l", BigDecimal.valueOf(2.3));
    private final static LinkedHashMap<Item, Integer> TEST_TRANSACTION_ITEMS_ORDERED;
    private final static BigDecimal TEST_TRANSACTION_SUM = BigDecimal.valueOf(5.9);

    static {
        TEST_TRANSACTION_ITEMS_ORDERED = new LinkedHashMap<Item, Integer>();
        TEST_TRANSACTION_ITEMS_ORDERED.put(TEST_PRODUCT1, 3);
        TEST_TRANSACTION_ITEMS_ORDERED.put(TEST_PRODUCT2, 1);
    }

    @Test
    public void shouldGetCorrectSum() {
        Transaction uut = Transaction.builder()
                .id(TEST_TRANSACTION_ID)
                .customerId(TEST_TRANSACTION_CUSTOMER_ID)
                .timestamp(TEST_TRANSACTION_TIMESTAMP)
                .itemsOrdered(TEST_TRANSACTION_ITEMS_ORDERED)
                .build();

        Assert.assertEquals(TEST_TRANSACTION_SUM, uut.getSum());
    }
}
