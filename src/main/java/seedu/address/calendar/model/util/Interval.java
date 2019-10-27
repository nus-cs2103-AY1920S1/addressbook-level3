package seedu.address.calendar.model.util;

public interface Interval<S extends IntervalPart<S>, T> extends Comparable<Interval<S, T>>  {
    boolean isEndsAfter(S intervalPart);
    boolean isStartsAfter(S intervalPart);
    boolean contains(S intervalPart);
    S getStart();
    S getEnd();
}
