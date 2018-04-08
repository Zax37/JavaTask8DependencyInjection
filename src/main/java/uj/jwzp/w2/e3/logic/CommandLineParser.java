package uj.jwzp.w2.e3.logic;

import joptsimple.OptionParser;
import joptsimple.util.PathConverter;
import uj.jwzp.w2.e3.logic.converter.DateRangeConverter;
import uj.jwzp.w2.e3.logic.converter.IntRangeConverter;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static uj.jwzp.w2.e3.logic.writer.TransactionWriter.AllowedOutputFormats;
import static uj.jwzp.w2.e3.logic.writer.TransactionWriter.AllowedOutputFormats.json;

public class CommandLineParser {
    private final static IntRangeConverter INT_RANGE_CONVERTER
            = new IntRangeConverter();
    private final static DateRangeConverter DATE_RANGE_CONVERTER
            = new DateRangeConverter();
    private final static PathConverter PATH_CONVERTER
            = new PathConverter();

    public final static IntRangeProperty DEFAULT_CUSTOMER_IDS = new IntRangeProperty(1, 20);
    public final static DateRangeProperty DEFAULT_DATE_RANGE;
    public final static Path DEFAULT_ITEMS_FILE = Paths.get("items.csv");
    public final static IntRangeProperty DEFAULT_ITEMS_COUNT = new IntRangeProperty(1, 5);
    public final static IntRangeProperty DEFAULT_ITEMS_QUANTITY = new IntRangeProperty(1, 5);
    public final static Integer DEFAULT_EVENTS_COUNT = 100;
    public final static Path DEFAULT_OUT_DIR = Paths.get("./");
    public final static AllowedOutputFormats DEFAULT_OUT_FORMAT = json;

    static {
        ZonedDateTime today = ZonedDateTime.now();
        DEFAULT_DATE_RANGE = new DateRangeProperty(
                today.with(LocalTime.MIN),
                today.with(LocalTime.MAX));
    }

    public static OptionParser getParser() {
        OptionParser parser = new OptionParser();
        parser.accepts("customerIds").withRequiredArg()
                .withValuesConvertedBy(INT_RANGE_CONVERTER)
                .defaultsTo(DEFAULT_CUSTOMER_IDS);
        parser.accepts("dateRange").withRequiredArg()
                .withValuesConvertedBy(DATE_RANGE_CONVERTER)
                .defaultsTo(DEFAULT_DATE_RANGE);
        parser.accepts("itemsFile").withRequiredArg()
                .withValuesConvertedBy(PATH_CONVERTER)
                .defaultsTo(DEFAULT_ITEMS_FILE);
        parser.accepts("itemsCount").withRequiredArg()
                .withValuesConvertedBy(INT_RANGE_CONVERTER)
                .defaultsTo(DEFAULT_ITEMS_COUNT);
        parser.accepts("itemsQuantity").withRequiredArg()
                .withValuesConvertedBy(INT_RANGE_CONVERTER)
                .defaultsTo(DEFAULT_ITEMS_QUANTITY);
        parser.accepts("eventsCount").withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(DEFAULT_EVENTS_COUNT);
        parser.accepts("outDir").withRequiredArg()
                .withValuesConvertedBy(PATH_CONVERTER)
                .defaultsTo(DEFAULT_OUT_DIR);
        parser.accepts("format").withRequiredArg()
                .ofType(AllowedOutputFormats.class)
                .defaultsTo(DEFAULT_OUT_FORMAT);
        return parser;
    }
}
