package seedu.address.model.events;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(DateTime, DateTime)}
 */
public class Timing {

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
    public static boolean isValidTiming(String testStart, String testEnd) {
        DateTime startDate = DateTime.tryParseSimpleDateFormat(testStart);
        DateTime endDate = DateTime.tryParseSimpleDateFormat(testEnd);
        return isValidTiming(startDate, endDate);
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */
    public static boolean isValidTiming(DateTime testStart, DateTime testEnd) {
        return testStart != null && testEnd != null && testStart.getTime().before(testEnd.getTime());
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
        return !(getEndTime().before(other.getStartTime())
                    || other.getStartTime().before(getStartTime()));
    }

    @Override
    public String toString() {
        return String.format("%s - %s", startTiming.toString(), endTiming.toString());
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
