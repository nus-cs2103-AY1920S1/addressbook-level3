package seedu.address.model.events;
//Date have problem.
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(DateTime, DateTime)}
 */
public class Timing {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
    private static int PERIOD = 30;

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

    /**
     * Constructs a {@code Timing}.
     *
     * @param stringTiming A valid dateTime describing the start of event.
     */
    public Timing(String stringTiming) {
        requireAllNonNull(stringTiming);
        checkArgument(isValidTiming(stringTiming), MESSAGE_CONSTRAINTS);
//        this.startTiming = formatter.parse(stringTiming);
//        this.endTiming = addMinutesToDate(PERIOD, startTiming);
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

    private static Date addMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
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

