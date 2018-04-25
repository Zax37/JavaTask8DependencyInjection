package uj.jwzp.w2.e3.logic.converter;

import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

@Component
public class IntRangeConverter implements
    ValueConverter<IntRangeProperty>, Converter<String, IntRangeProperty> {

    @Override
    public IntRangeProperty convert(String value) {
        String[] parts = value.split(":");
        if (parts.length != 2) {
            throw new ValueConversionException("Couldn't parse this IntRange: "+value);
        }
        return new IntRangeProperty(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }

    @Override
    public Class<? extends IntRangeProperty> valueType() {
        return IntRangeProperty.class;
    }

    @Override
    public String valuePattern() {
        return null;
    }
}
