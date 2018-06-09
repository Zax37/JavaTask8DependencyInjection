package uj.jwzp.w2.e3.logic.converter;

import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class DateRangeConverter implements
    ValueConverter<DateRangeProperty>, Converter<String, DateRangeProperty> {

    private final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);

    @Override
    public DateRangeProperty convert(String value) {
        if (value.length()%2 != 1 || value.charAt(value.length()/2) != ':') { //two dates of same format, separated by :
            throw new ValueConversionException("Couldn't parse this DateRange: "+value);
        }
        final int lengthOfSingle = (value.length() - 1) / 2;
        String first, second;
        if (value.charAt(0) == '"') {
            first = value.substring(1, lengthOfSingle-1);
            second = value.substring(lengthOfSingle+2, lengthOfSingle*2);
        } else {
            first = value.substring(0, lengthOfSingle);
            second = value.substring(lengthOfSingle+1, lengthOfSingle*2+1);
        }
        return new DateRangeProperty(
                ZonedDateTime.parse(first, DATE_TIME_FORMATTER),
                ZonedDateTime.parse(second, DATE_TIME_FORMATTER)
        );
    }

    @Override
    public Class<? extends DateRangeProperty> valueType() {
        return DateRangeProperty.class;
    }

    @Override
    public String valuePattern() {
        return null;
    }
}
