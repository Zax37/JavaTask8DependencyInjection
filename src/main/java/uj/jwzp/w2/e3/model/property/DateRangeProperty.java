package uj.jwzp.w2.e3.model.property;

import lombok.Value;
import uj.jwzp.w2.e3.logic.converter.DateRangeConverter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Value
public class DateRangeProperty implements Property {
    private ZonedDateTime from;
    private ZonedDateTime to;

    public DateRangeProperty(ZonedDateTime val1, ZonedDateTime val2) {
        from = val1;
        to = val2;
    }

    @Override
    public String toString() {
        return DateRangeConverter.revert(this);
    }

    public ZonedDateTime random() {
        return from.plusNanos(Math.round(Math.random() * from.until(to, ChronoUnit.NANOS)));
    }
}
