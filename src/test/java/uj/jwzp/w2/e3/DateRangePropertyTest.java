package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateRangePropertyTest {
    private final static ZonedDateTime YEAR_BEGINING = ZonedDateTime.of(
            1995, 1, 1, 0, 0, 0, 0, ZoneId.of("GMT+1"));
    private final static ZonedDateTime YEAR_END = ZonedDateTime.of(
            1995, 12, 31, 23, 59, 59, 999999999, ZoneId.of("GMT+1"));
    private final static String STRING_RANGE = "1995-01-01T00:00:00.000+0100:1995-12-31T23:59:59.999+0100";

    @Test
    public void shouldReturnRandomInRange() {
        DateRangeProperty uut = new DateRangeProperty(
                YEAR_BEGINING,
                YEAR_END
        );
        ZonedDateTime randomBetween = uut.random();
        Assert.assertTrue(randomBetween.compareTo(YEAR_BEGINING) >= 0);
        Assert.assertTrue(randomBetween.compareTo(YEAR_END) <= 0);
    }

    @Test
    public void shouldConvertToStringProperly() {
        DateRangeProperty uut = new DateRangeProperty(
                YEAR_BEGINING,
                YEAR_END
        );

        Assert.assertEquals(STRING_RANGE, uut.toString());
    }
}
