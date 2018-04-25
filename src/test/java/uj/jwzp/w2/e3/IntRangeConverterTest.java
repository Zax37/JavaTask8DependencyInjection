package uj.jwzp.w2.e3;

import joptsimple.ValueConversionException;
import org.junit.Assert;
import org.junit.Test;
import uj.jwzp.w2.e3.logic.converter.IntRangeConverter;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

public class IntRangeConverterTest {
    private final static int FIRST_NUMBER = 1;
    private final static int SECOND_NUMBER = 10;
    private final static String STRING_RANGE = "1:10";
    private final static String STRING_RANGE_INCORRECT_ONE_SECTION = "1";
    private final static String STRING_RANGE_INCORRECT_THREE_SECTIONS = "1:2:3";

    @Test
    public void shouldConvertStandardRange() {
        IntRangeConverter uut = new IntRangeConverter();

        IntRangeProperty result = uut.convert(STRING_RANGE);

        Assert.assertEquals(FIRST_NUMBER, result.getFrom());
        Assert.assertEquals(SECOND_NUMBER, result.getTo());
    }

    @Test(expected = ValueConversionException.class)
    public void shouldFailWhenJustOneSection() {
        IntRangeConverter uut = new IntRangeConverter();

        IntRangeProperty result = uut.convert(STRING_RANGE_INCORRECT_ONE_SECTION);
    }

    @Test(expected = ValueConversionException.class)
    public void shouldFailWhenMoreThanTwoSections() {
        IntRangeConverter uut = new IntRangeConverter();

        IntRangeProperty result = uut.convert(STRING_RANGE_INCORRECT_THREE_SECTIONS);
    }
}
