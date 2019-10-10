package seedu.address.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A Duration contains a start time and an end time.
 */
public class Duration implements Comparable<Duration> {

    private static final DateTimeFormatter COMPACT_TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");

    private LocalTime start;
    private LocalTime end;

    private Duration(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
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
    public static Duration parse(String startTime, String endTime) throws DateTimeParseException {
        // append front with zero
        String first = String.format("%04d", Integer.parseInt(startTime));
        String second = String.format("%04d", Integer.parseInt(endTime));

        return new Duration(LocalTime.parse(first, COMPACT_TIME_FORMAT), LocalTime.parse(second, COMPACT_TIME_FORMAT));
    }

    /**
     * Checks whether the two durations overlap.
     * @param other the other duration
     * @return whether they overlap
     */
    public boolean overlaps(Duration other) {
        Duration early = this.compareTo(other) > 0 ? other : this;
        Duration late = this.compareTo(other) > 0 ? this : other;

        return early.getEnd().compareTo(late.getStart()) > 0;
    }

    public LocalTime getEnd() {
        return end;
    }

    public LocalTime getStart() {
        return start;
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
                || (obj instanceof Duration // instanceof handles nulls
                && this.start.equals(((Duration) obj).start)
                && this.end.equals(((Duration) obj).end)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    @Override
    public int compareTo(Duration o) {
        return !this.getStart().equals(o.getStart())
                ? this.getStart().compareTo(o.getStart())
                : this.getEnd().compareTo(o.getEnd());
    }
}
