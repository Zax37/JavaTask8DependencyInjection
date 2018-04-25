package uj.jwzp.w2.e3;

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
    private final static String TEST_FORMAT_ARGUMENT = "-format=xml";
    private final static TransactionWriter.AllowedOutputFormats TEST_FORMAT_EXPECTED
            = TransactionWriter.AllowedOutputFormats.xml;
    private final static TransactionWriter.AllowedOutputFormats TEST_FORMAT_DEFAULT
            = TransactionWriter.AllowedOutputFormats.json;

    @Before
    public void Prepare() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void GetSingleArgument() {
        CommandLineParser uut = new CommandLineParser(
                intRangeConverter,
                dateRangeConverter,
                pathConverter,
                defaultArgs
        );

        OptionSet result = uut.getParser().parse(TEST_FORMAT_ARGUMENT);

        Assert.assertTrue(result.has(TEST_FORMAT_OPTION));
        Assert.assertEquals(TEST_FORMAT_EXPECTED, result.valueOf(TEST_FORMAT_OPTION));
    }

    @Test
    public void GetDefaultFormat() {
        CommandLineParser uut = new CommandLineParser(
                intRangeConverter,
                dateRangeConverter,
                pathConverter,
                defaultArgs
        );

        OptionSet result = uut.getParser().parse("");

        Assert.assertEquals(TEST_FORMAT_DEFAULT, result.valueOf(TEST_FORMAT_OPTION));
    }
}
