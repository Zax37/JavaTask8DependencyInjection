package uj.jwzp.w2.e3.model;

import lombok.Value;
import lombok.experimental.NonFinal;
import uj.jwzp.w2.e3.model.property.DateRangeProperty;
import uj.jwzp.w2.e3.model.property.IntRangeProperty;

@Value
@NonFinal
public class TransactionGeneratorArgs {
    private final IntRangeProperty customerIds;
    private final DateRangeProperty dateRange;
    private final IntRangeProperty itemsCount;
    private final IntRangeProperty itemsQuantity;
    private final Integer eventsCount;

    public TransactionGeneratorArgs(
            IntRangeProperty customerIds,
            DateRangeProperty dateRange,
            IntRangeProperty itemsCount,
            IntRangeProperty itemsQuantity,
            Integer eventsCount
    ) {
        this.customerIds = customerIds;
        this.dateRange = dateRange;
        this.itemsCount = itemsCount;
        this.itemsQuantity = itemsQuantity;
        this.eventsCount = eventsCount;
    }
}
