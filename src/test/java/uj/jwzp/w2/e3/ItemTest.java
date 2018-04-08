package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import uj.jwzp.w2.e3.model.Item;

import java.math.BigDecimal;

public class ItemTest {
    private final static String TEST_ITEM_NAME = "i";
    private final static BigDecimal TEST_ITEM_PRICE = BigDecimal.valueOf(3);

    @Test
    public void shouldReturnSameAsInput() {
        Item uut = new Item(TEST_ITEM_NAME, TEST_ITEM_PRICE);

        Assert.assertEquals(TEST_ITEM_NAME, uut.getName());
        Assert.assertEquals(TEST_ITEM_NAME, uut.toString());
        Assert.assertEquals(TEST_ITEM_PRICE, uut.getPrice());
    }
}
