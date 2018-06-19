package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uj.jwzp.w2.e3.logic.TransactionsGenerator;
import uj.jwzp.w2.e3.logic.beans.ConnectionFactoryConfig;
import uj.jwzp.w2.e3.logic.beans.TransactionGeneratorArgsSource;
import uj.jwzp.w2.e3.logic.reader.ItemsReader;
import uj.jwzp.w2.e3.logic.writer.JmsPublisher;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;
import uj.jwzp.w2.e3.model.Item;
import uj.jwzp.w2.e3.model.Transaction;
import uj.jwzp.w2.e3.model.TransactionGeneratorArgs;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;

public class TransactionsGeneratorTest {
    private final static int TRANSACTION_ID = 1;
    private final static int TRANSACTION_CUSTOMER_ID = 2;
    private final static ZonedDateTime TRANSACTION_DATE = ZonedDateTime.now();
    private final static int TRANSACTION_ITEMS_QUANTITY = 1;

    private final static BigDecimal TRANSACTION_ITEM1_PRICE = BigDecimal.valueOf(2.5);
    private final static BigDecimal TRANSACTION_ITEM2_PRICE = BigDecimal.valueOf(1.2);
    private final static BigDecimal TRANSACTION_SUMMARY = BigDecimal.valueOf(3.7);

    private final static int EVENTS_COUNT_SINGLE = 1;
    private final static int EVENTS_COUNT_MULTIPLE = 3;

    @InjectMocks
    private TransactionGeneratorArgsSource argsSource;

    private JmsPublisher publisher = Mockito.mock(JmsPublisher.class);

    @Before
    public void Prepare() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetSingleRandomTransactionWithDefaultArgs() {
        ItemsReader reader = Mockito.mock(ItemsReader.class);
        TransactionWriter writer = Mockito.mock(TransactionWriter.class);

        Item testItem = Mockito.mock(Item.class);
        List<Item> availableItemsList = new ArrayList<>();
        availableItemsList.add(testItem);

        TransactionGeneratorArgs args = argsSource.getTransactionGeneratorArgs(
                new IntRangeProperty(TRANSACTION_CUSTOMER_ID, TRANSACTION_CUSTOMER_ID),
                new DateRangeProperty(TRANSACTION_DATE, TRANSACTION_DATE),
                new IntRangeProperty(availableItemsList.size(), availableItemsList.size()),
                new IntRangeProperty(TRANSACTION_ITEMS_QUANTITY, TRANSACTION_ITEMS_QUANTITY),
                EVENTS_COUNT_SINGLE );

        TransactionsGenerator uut = new TransactionsGenerator(reader, writer, args, publisher);
        Transaction result = uut.getSingleRandomTransaction(availableItemsList, TRANSACTION_ID);

        Assert.assertEquals(TRANSACTION_ID, result.getId());
        Assert.assertEquals(TRANSACTION_CUSTOMER_ID, result.getCustomerId());
        Assert.assertEquals(TRANSACTION_DATE, result.getTimestamp());
        Assert.assertEquals(TRANSACTION_ITEMS_QUANTITY, (int)result.getItemsOrdered().get(testItem));
    }

    @Test
    public void shouldCountSummaryPrice() {
        ItemsReader reader = Mockito.mock(ItemsReader.class);
        TransactionWriter writer = Mockito.mock(TransactionWriter.class);

        Item testItem1 = Mockito.mock(Item.class);
        Item testItem2 = Mockito.mock(Item.class);
        List<Item> availableItemsList = new ArrayList<Item>();
        availableItemsList.add(testItem1);
        availableItemsList.add(testItem2);
        Mockito.when(testItem1.getPrice()).thenReturn(TRANSACTION_ITEM1_PRICE);
        Mockito.when(testItem2.getPrice()).thenReturn(TRANSACTION_ITEM2_PRICE);

        TransactionGeneratorArgs args = new TransactionGeneratorArgs(
                new IntRangeProperty(TRANSACTION_CUSTOMER_ID, TRANSACTION_CUSTOMER_ID),
                new DateRangeProperty(TRANSACTION_DATE, TRANSACTION_DATE),
                new IntRangeProperty(availableItemsList.size(), availableItemsList.size()),
                new IntRangeProperty(TRANSACTION_ITEMS_QUANTITY, TRANSACTION_ITEMS_QUANTITY),
                EVENTS_COUNT_SINGLE );

        TransactionsGenerator uut = new TransactionsGenerator(reader, writer, args, publisher);
        Transaction result = uut.getSingleRandomTransaction(availableItemsList, TRANSACTION_ID);

        Assert.assertEquals(TRANSACTION_SUMMARY, result.getSum());
    }

    @Test
    public void shouldGenerateAll() {
        ItemsReader reader = Mockito.mock(ItemsReader.class);
        TransactionWriter writer = Mockito.mock(TransactionWriter.class);

        Item testItem1 = Mockito.mock(Item.class);
        List<Item> availableItemsList = new ArrayList<Item>();
        availableItemsList.add(testItem1);
        Mockito.when(testItem1.getPrice()).thenReturn(TRANSACTION_ITEM1_PRICE);

        TransactionGeneratorArgs args = new TransactionGeneratorArgs(
                new IntRangeProperty(TRANSACTION_CUSTOMER_ID, TRANSACTION_CUSTOMER_ID),
                new DateRangeProperty(TRANSACTION_DATE, TRANSACTION_DATE),
                new IntRangeProperty(availableItemsList.size(), availableItemsList.size()),
                new IntRangeProperty(TRANSACTION_ITEMS_QUANTITY, TRANSACTION_ITEMS_QUANTITY),
                EVENTS_COUNT_MULTIPLE );

        TransactionsGenerator uut = new TransactionsGenerator(reader, writer, args, publisher);
        try {
            Mockito.when(reader.read()).thenReturn(availableItemsList);
            uut.generate();
            Mockito.verify(writer, Mockito.times(EVENTS_COUNT_MULTIPLE)).write(Mockito.any());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void shouldLogAndRethrowWhenFailedWritingTransaction() {
        PrintStream sysOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ItemsReader reader = Mockito.mock(ItemsReader.class);
        TransactionWriter writer = Mockito.mock(TransactionWriter.class);

        Item testItem = Mockito.mock(Item.class);
        List<Item> availableItemsList = new ArrayList<>();
        availableItemsList.add(testItem);

        TransactionGeneratorArgs args = new TransactionGeneratorArgs (
                new IntRangeProperty(TRANSACTION_CUSTOMER_ID, TRANSACTION_CUSTOMER_ID),
                new DateRangeProperty(TRANSACTION_DATE, TRANSACTION_DATE),
                new IntRangeProperty(availableItemsList.size(), availableItemsList.size()),
                new IntRangeProperty(TRANSACTION_ITEMS_QUANTITY, TRANSACTION_ITEMS_QUANTITY),
                EVENTS_COUNT_SINGLE );

        TransactionsGenerator uut = new TransactionsGenerator(reader, writer, args, publisher);

        try {
            Mockito.when(reader.read()).thenReturn(availableItemsList);
            Mockito.doThrow(new IOException()).when(writer).write(Mockito.any());
            uut.generate();
            Assert.fail();
        } catch (IOException e) {
            System.setOut(sysOut);
            Assert.assertThat(outContent.toString(), containsString("ERROR"));
        }
    }
}
