package seedu.address.commons.util;

import java.util.Date;

public class DatePair implements Comparable<DatePair> {
    int key;
    Date value;

    public DatePair(int key, Date value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public Date getValue() {
        return value;
    }

    @Override
    public int compareTo(DatePair o) {
        return value.compareTo(o.getValue());
    }
}
