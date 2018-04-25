package uj.jwzp.w2.e3.model.property;

import lombok.AllArgsConstructor;
import lombok.Value;
import uj.jwzp.w2.e3.logic.converter.IntRangeConverter;

@Value
@AllArgsConstructor
public class IntRangeProperty implements Property {
    private int from;
    private int to;

    @Override
    public String toString() {
        return from + ":" + to;
    }

    public int random() {
        return from + (int)Math.round(Math.random() * (to - from));
    }
}
