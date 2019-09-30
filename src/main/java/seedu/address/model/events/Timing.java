package seedu.address.model.events;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

//TODO: Stub models for now
/**
 * Represents a Date time in the schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(Date, Date)}
 */
public class Timing {

    public static final String MESSAGE_CONSTRAINTS =
        "The event start datetime must be before the end datetime.";
    public static final String DATETIME_FORMAT = "dd MMM yyyy HHmm";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATETIME_FORMAT);

    private final Date startTiming;
    private final Date endTiming;

    /**
     * Constructs a {@code Timing}.
     *
     * @param startTiming A valid dateTime describing the start of event.
     * @param endTiming   A valid dateTime describing the end of event.
     */
    public Timing(Date startTiming, Date endTiming) {
        requireAllNonNull(startTiming, endTiming);
        checkArgument(isValidTiming(startTiming, endTiming), MESSAGE_CONSTRAINTS);

        this.startTiming = startTiming;
        this.endTiming = endTiming;
    }

    /**
     * Parses a simple date format as described by {@code DATETIME_FORMAT}, else returns null.
     *
     * @param dateString a simple date format
     *
     * @return a date if valid, otherwise, returns null.
     */
    private static Date parseSimpleDateFormat(String dateString) {
        Date parsedDate;
        try {
            parsedDate = DATE_FORMATTER.parse(dateString);
        } catch (ParseException ex) {
            parsedDate = null;
        }
        return parsedDate;
    }

    private static String toString(Date date) {
        return DATE_FORMATTER.format(date);
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */
    public static boolean isValidTiming(String testStart, String testEnd) {
        Date startDate = parseSimpleDateFormat(testStart);
        Date endDate = parseSimpleDateFormat(testEnd);
        return isValidTiming(startDate, endDate);
    }

    /**
     * Returns true if the start dateTime is before the end dateTime.
     */
    public static boolean isValidTiming(Date testStart, Date testEnd) {
        return testStart != null && testEnd != null && testStart.before(testEnd);
    }

    public Date getStartTiming() {
        return startTiming;
    }

    public Date getEndTiming() {
        return endTiming;
    }

    public String toStartTimingString() {
        return toString(startTiming);
    }

    public String toEndTimingString() {
        return toString(endTiming);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", toStartTimingString(), toEndTimingString());
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
        return otherTiming.getStartTiming().equals(getStartTiming())
            && otherTiming.getEndTiming().equals(getEndTiming());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTiming, endTiming);
    }
}
