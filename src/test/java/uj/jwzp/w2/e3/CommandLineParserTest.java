package uj.jwzp.w2.e3;

import joptsimple.OptionException;
import joptsimple.OptionSet;
import joptsimple.util.PathConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import uj.jwzp.w2.e3.logic.CommandLineParser;
import uj.jwzp.w2.e3.logic.beans.DefaultCommandLineArgs;
import uj.jwzp.w2.e3.logic.converter.DateRangeConverter;
import uj.jwzp.w2.e3.logic.converter.IntRangeConverter;
import uj.jwzp.w2.e3.logic.writer.TransactionWriter;

public class CommandLineParserTest {
    @InjectMocks
    IntRangeConverter intRangeConverter;

    @InjectMocks
    DateRangeConverter dateRangeConverter;

    @InjectMocks
    PathConverter pathConverter;

    @InjectMocks
    DefaultCommandLineArgs defaultArgs;

    private final static String TEST_FORMAT_OPTION = "format";
    private final static String TEST_BROKER_ARGUMENT = "-broker=www.broker.pl";
    private final static String TEST_QUEUE_ARGUMENT = "-queue=queue";
    private final static String TEST_TOPIC_ARGUMENT = "-topic=topic";
    private final static String TEST_QUEUE_VALUE = "queue";
    private final static String TEST_TOPIC_VALUE = "topic";
    private final static String TEST_FORMAT_ARGUMENT = "-format=xml";
    private final static TransactionWriter.AllowedOutputFormats TEST_FORMAT_EXPECTED
            = TransactionWriter.AllowedOutputFormats.xml;
    private final static TransactionWriter.AllowedOutputFormats TEST_FORMAT_DEFAULT
            = TransactionWriter.AllowedOutputFormats.json;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSingleArgument() {
        CommandLineParser uut = new CommandLineParser(
                intRangeConverter,
                dateRangeConverter,
                pathConverter,
                defaultArgs
        );

        OptionSet result = uut.getParser().parse(TEST_BROKER_ARGUMENT, TEST_QUEUE_ARGUMENT, TEST_FORMAT_ARGUMENT);

        Assert.assertTrue(result.has(TEST_FORMAT_OPTION));
        Assert.assertEquals(TEST_FORMAT_EXPECTED, result.valueOf(TEST_FORMAT_OPTION));
    }

    @Test
    public void getDefaultFormat() {
        CommandLineParser uut = new CommandLineParser(
                intRangeConverter,
                dateRangeConverter,
                pathConverter,
                defaultArgs
        );

        OptionSet result = uut.getParser().parse(TEST_BROKER_ARGUMENT, TEST_QUEUE_ARGUMENT);

        Assert.assertEquals(TEST_FORMAT_DEFAULT, result.valueOf(TEST_FORMAT_OPTION));
    }

    @Test
    public void queueOrTopicNeededWithBroker() {
        CommandLineParser uut = new CommandLineParser(
                intRangeConverter,
                dateRangeConverter,
                pathConverter,
                defaultArgs
        );

        boolean thrown = false;
        try {
            uut.getParser().parse("");
        } catch (OptionException exception) {
            thrown = true;
        }
        Assert.assertTrue(thrown);

        OptionSet result1 = uut.getParser().parse(TEST_BROKER_ARGUMENT, TEST_QUEUE_ARGUMENT);
        OptionSet result2 = uut.getParser().parse(TEST_BROKER_ARGUMENT, TEST_TOPIC_ARGUMENT);

        Assert.assertEquals(TEST_QUEUE_VALUE, result1.valueOf(TEST_QUEUE_VALUE));
        Assert.assertEquals(TEST_TOPIC_VALUE, result2.valueOf(TEST_TOPIC_VALUE));
    }
}
