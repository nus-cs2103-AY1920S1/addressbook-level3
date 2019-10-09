package seedu.address.model.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(DateTime, DateTime)}
 */
public class Timing implements Comparable<Timing> {

    public static final String MESSAGE_CONSTRAINTS =
        "The event start timing must be before the end timing.";

    private final DateTime startTiming;
    private final DateTime endTiming;

    /**
     * Constructs a {@code Timing}.
     *
     * @param startTiming A valid dateTime describing the start of event.
     * @param endTiming   A valid dateTime describing the end of event.
     */
    public Timing(DateTime startTiming, DateTime endTiming) {
        requireAllNonNull(startTiming, endTiming);
        checkArgument(isValidTiming(startTiming, endTiming), MESSAGE_CONSTRAINTS);

        this.startTiming = startTiming;
        this.endTiming = endTiming;
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */
    public static boolean isValidTiming(DateTime testStart, DateTime testEnd) {
        requireAllNonNull(testStart, testEnd);
        return testStart.before(testEnd);
    }

    public DateTime getStartTime() {
        return startTiming;
    }

    public DateTime getEndTime() {
        return endTiming;
    }

    /**
     * Returns true if the another timing is staggering within the start or end dateTime.
     */
    public boolean conflictsWith(Timing other) {
        requireNonNull(other);
        return other != this
                && getStartTime().before(other.getEndTime())
                && other.getStartTime().before(getEndTime());
    }

    @Override
    public String toString() {
        return String.format("%s - %s", startTiming.toString(), endTiming.toString());
    }

    @Override
    public int compareTo(Timing t) {
        requireNonNull(t);
        int cmpStartTimingResult = getStartTime().compareTo(t.getStartTime());
        if (cmpStartTimingResult != 0) {
            return cmpStartTimingResult;
        }

        return getEndTime().compareTo(t.getEndTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timing)) {
            return false;
        }

        Timing otherTiming = (Timing) other;
        return otherTiming.getStartTime().equals(getStartTime())
            && otherTiming.getEndTime().equals(getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTiming, endTiming);
    }
}
