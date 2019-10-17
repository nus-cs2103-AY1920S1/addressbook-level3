package seedu.address.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A EventTime contains a start time and an end time.
 */
public class EventTime implements Comparable<EventTime> {

    public static final String TIME_FORMAT = "HHmm";
    public static final DateTimeFormatter COMPACT_TIME_FORMAT = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");

    public static final String MESSAGE_CONSTRAINTS = "Duration needs to have a start and end time. "
            + "Format: " + TIME_FORMAT + " - " + TIME_FORMAT + ". "
            + "Example: 1130 - 1300.";

    private LocalTime start;
    private LocalTime end;


    public EventTime(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public EventTime(LocalTime start, Duration duration) {
        this.start = start;
        this.end = start.plus(duration);
    }

    /**
     * Builds duration from two text representations of time.
     * <p>
     * This methods appends zeros to the front of the input, if
     * the input is less than 4 digits. For example, "900" will be changed into "0900".
     *
     * @param startTime start time
     * @param endTime end time
     * @return the duration with the specified start and end time
     */
    public static EventTime parse(String startTime, String endTime) throws DateTimeParseException {
        // append front with zero
        String first = String.format("%04d", Integer.parseInt(startTime));
        String second = String.format("%04d", Integer.parseInt(endTime));

        return new EventTime(LocalTime.parse(first, COMPACT_TIME_FORMAT), LocalTime.parse(second, COMPACT_TIME_FORMAT));
    }

    /**
     * Checks whether the two durations overlap.
     * @param other the other duration
     * @return true if they overlap
     */
    public boolean overlaps(EventTime other) {
        EventTime early = this.compareTo(other) > 0 ? other : this;
        EventTime late = this.compareTo(other) > 0 ? this : other;

        return early.getEnd().compareTo(late.getStart()) > 0;
    }

    public LocalTime getEnd() {
        return end;
    }

    public LocalTime getStart() {
        return start;
    }

    public static String getStringFromTime(LocalTime time) {
        return time.format(COMPACT_TIME_FORMAT);
    }

    /**
     * Checks if {@code String startTime} and {@code String endTime} are valid duration
     */
    public static boolean isValidDuration(String startTime, String endTime) {
        try {
            parse(startTime, endTime);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    public Duration getDuration() {
        return Duration.between(this.start, this.end);
    }

    /**
     * Outputs the duration as a string, in HH:mm format.
     * @return the duration in string
     */
    public String to24HrString() {
        return String.format("%s - %s", start.toString(), end.toString());
    }

    @Override
    public String toString() {
        return String.format("%s - %s", start.format(DISPLAY_TIME_FORMAT), end.format(DISPLAY_TIME_FORMAT));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof EventTime // instanceof handles nulls
                && this.start.equals(((EventTime) obj).start)
                && this.end.equals(((EventTime) obj).end)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    @Override
    public int compareTo(EventTime o) {
        return !this.getStart().equals(o.getStart())
                ? this.getStart().compareTo(o.getStart())
                : this.getEnd().compareTo(o.getEnd());
    }
}
