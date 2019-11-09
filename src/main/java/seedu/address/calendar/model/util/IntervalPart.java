package seedu.address.calendar.model.util;

/**
 * Represents a part of an interval (i.e. the start or the end).
 * @param <T> The type of interval part (e.g. time, date, integer, etc.)
 */
public interface IntervalPart<T> extends Comparable<IntervalPart<T>> {
    T copy();
}
