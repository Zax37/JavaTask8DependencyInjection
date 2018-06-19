package uj.jwzp.w2.e3.logic;

import joptsimple.OptionParser;
import joptsimple.util.PathConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uj.jwzp.w2.e3.logic.beans.DefaultCommandLineArgs;
import uj.jwzp.w2.e3.logic.converter.DateRangeConverter;
import uj.jwzp.w2.e3.logic.converter.IntRangeConverter;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static uj.jwzp.w2.e3.logic.writer.TransactionWriter.AllowedOutputFormats;

@Component
public class CommandLineParser {
    private IntRangeConverter intRangeConverter;
    private DateRangeConverter dateRangeConverter;
    private PathConverter pathConverter;

    private IntRangeProperty defaultCustomerIds;
    private DateRangeProperty defaultDateRange;
    private Path defaultItemsFile;
    private IntRangeProperty defaultItemsCount;
    private IntRangeProperty defaultItemsQuantity;
    private Integer defaultEventsCount;
    private Path defaultOutDir;
    private AllowedOutputFormats defaultOutFormat;

    @Value("${broker}") String broker;

    @Autowired
    public CommandLineParser(
            IntRangeConverter intRangeConverter,
            DateRangeConverter dateRangeConverter,
            PathConverter pathConverter,
            DefaultCommandLineArgs defaultArgs
    ) {
        this.intRangeConverter = intRangeConverter;
        this.dateRangeConverter = dateRangeConverter;
        this.pathConverter = pathConverter;

        this.defaultCustomerIds = defaultArgs.getDefaultCustomerIds();
        this.defaultDateRange = defaultArgs.getDefaultDateRange();
        this.defaultItemsFile = defaultArgs.getDefaultItemsFile();
        this.defaultItemsCount = defaultArgs.getDefaultItemsCount();
        this.defaultItemsQuantity = defaultArgs.getDefaultItemsQuantity();
        this.defaultEventsCount = defaultArgs.getDefaultEventsCount();
        this.defaultOutDir = defaultArgs.getDefaultOutDir();
        this.defaultOutFormat = defaultArgs.getDefaultOutFormat();
    }

    public OptionParser getParser() {
        OptionParser parser = new OptionParser();
        parser.accepts("customerIds").withRequiredArg()
                .withValuesConvertedBy(intRangeConverter)
                .defaultsTo(defaultCustomerIds);
        parser.accepts("dateRange").withRequiredArg()
                .withValuesConvertedBy(dateRangeConverter)
                .defaultsTo(defaultDateRange);
        parser.accepts("itemsFile").withRequiredArg()
                .withValuesConvertedBy(pathConverter)
                .defaultsTo(defaultItemsFile);
        parser.accepts("itemsCount").withRequiredArg()
                .withValuesConvertedBy(intRangeConverter)
                .defaultsTo(defaultItemsCount);
        parser.accepts("itemsQuantity").withRequiredArg()
                .withValuesConvertedBy(intRangeConverter)
                .defaultsTo(defaultItemsQuantity);
        parser.accepts("eventsCount").withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(defaultEventsCount);
        parser.accepts("outDir").withRequiredArg()
                .withValuesConvertedBy(pathConverter)
                .defaultsTo(defaultOutDir);
        parser.accepts("format").withRequiredArg()
                .ofType(AllowedOutputFormats.class)
                .defaultsTo(defaultOutFormat);
        if (broker == null) {
            parser.accepts("broker").requiredUnless("outDir").withRequiredArg();
            List<String> brokerArgs = new ArrayList<>();
            brokerArgs.add("queue");
            brokerArgs.add("topic");
            parser.acceptsAll(brokerArgs)
                    .availableIf("broker").requiredIf("broker").withRequiredArg();
        }
        return parser;
    }
}
