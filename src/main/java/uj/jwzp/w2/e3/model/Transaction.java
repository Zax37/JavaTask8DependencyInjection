package uj.jwzp.w2.e3.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@Value
@NonFinal
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Transaction.class)
public class Transaction {
    private final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);

    private int id;
    private ZonedDateTime timestamp;
    private int customerId;
    private LinkedHashMap<Item, Integer> itemsOrdered;

    public BigDecimal getSum() {
        BigDecimal ret = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> entry : itemsOrdered.entrySet()) {
            ret = ret.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return ret;
    }
}