package seedu.address.calendar.model.util;

public interface IntervalPart<T> extends Comparable<IntervalPart<T>> {
    T copy();
}
