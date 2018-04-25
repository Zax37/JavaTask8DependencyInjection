package uj.jwzp.w2.e3;

import joptsimple.ValueConversionException;
import org.junit.Assert;
import org.junit.Test;
import uj.jwzp.w2.e3.logic.converter.DateRangeConverter;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static uj.jwzp.w2.e3.logic.converter.DateRangeConverter.DATE_TIME_FORMATTER;

public class DateRangeConverterTest {
    private final static ZonedDateTime YEAR_BEGINING = ZonedDateTime.of(
            1995, 1, 1, 0, 0, 0, 0, ZoneId.of("+01:00"));
    private final static ZonedDateTime YEAR_END = ZonedDateTime.of(
            1995, 12, 31, 23, 59, 59, 999999999, ZoneId.of("+01:00"));
    private final static String STRING_RANGE = "1995-01-01T00:00:00.000+0100:1995-12-31T23:59:59.999+0100";
    private final static String STRING_RANGE_INCORRECT = "1995-01-01:1995-12-31T23:59:59.999+0100";
    private final static String STRING_RANGE_INCORRECT2 = "1995-01-01:1995-12-31T";

    @Test
    public void shouldConvertStandardRange() {
        DateRangeConverter uut = new DateRangeConverter();

        DateRangeProperty result = uut.convert(STRING_RANGE);

        Assert.assertEquals(YEAR_BEGINING.format(DATE_TIME_FORMATTER), result.getFrom().format(DATE_TIME_FORMATTER));
        Assert.assertEquals(YEAR_END.format(DATE_TIME_FORMATTER), result.getTo().format(DATE_TIME_FORMATTER));
    }

    @Test(expected = ValueConversionException.class)
    public void shouldFailWhenOneDateIsInDifferentFormatThanTheOtherOne() {
        DateRangeConverter uut = new DateRangeConverter();

        DateRangeProperty result = uut.convert(STRING_RANGE_INCORRECT);
    }

    @Test(expected = ValueConversionException.class)
    public void shouldFailWhenStringCannotBeDividedIntoTwoParts() {
        DateRangeConverter uut = new DateRangeConverter();

        DateRangeProperty result = uut.convert(STRING_RANGE_INCORRECT2);
    }
}
