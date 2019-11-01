package seedu.address.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A EventTime contains a start time and an end time.
 */
public class EventTime implements Comparable<EventTime> {

    /**
     * The first and last 4 digits can be from 0 to 2359, includes optional leading zeros.
     * There must be a "-" in middle the 2 time formats.
     * There must have also a white space in front and behind of the dash.
     */
    public static final String VALIDATION_REGEX = "^(0?[0-9]?[0-5]?[0-9]|1[0-9][0-5][0-9]|2[0-3][0-5][0-9])"
                                                    + "\\s*-\\s*"
                                                    + "(0?[0-9]?[0-5]?[0-9]|1[0-9][0-5][0-9]|2[0-3][0-5][0-9])$";

    public static final String TIME_FORMAT = "HHmm";
    public static final DateTimeFormatter COMPACT_TIME_FORMAT = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");

    private static final DateTimeFormatter JSON_FORMATTER = DateTimeFormatter.ofPattern("Hmm");

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
     * Gets EventTime Object representation of duration of delivery task.
     * Check using {@code isValidEventTime(String duration) } before parse.
     *
     * @param duration startTime - endTime.
     * @return the duration with the specified start and end time
     */
    public static EventTime parse(String duration) throws DateTimeParseException {
        assert isValidEventTime(duration) : "duration is not following the correct format. Eg. 1230 - 1420.";

        List<String> times = Stream.of(duration.split("-")).map(String::trim).collect(Collectors.toList());

        return parse(times.get(0), times.get(1));
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

    /**
     * Convert EventTime Object to user input duration format for json file.
     * Example: 1200 - 1330.
     */
    public static String getStringFromDuration(EventTime duration) {
        String startTime = duration.getStart().format(JSON_FORMATTER);
        String endTime = duration.getEnd().format(JSON_FORMATTER);
        return startTime + " - " + endTime;
    }

    /**
     * Checks if {@code String duration} is a valid duration.
     */
    public static boolean isValidEventTime(String duration) {
        if (!duration.matches(VALIDATION_REGEX)) {
            return false;
        }

        //split string into 3 parts to get start time, "-" and end time
        List<String> times = Stream.of(duration.split("-")).map(String::trim).collect(Collectors.toList());
        String startTimeStr = times.get(0);
        String endTimeStr = times.get(1);

        try {
            int startTime = Integer.parseInt(startTimeStr);
            int endTime = Integer.parseInt(endTimeStr);

            //checks if Start time is before End time
            if (endTime <= startTime) {
                return false;
            }

            //checks if it can be parse into a LocalDate Object
            parse(startTimeStr, endTimeStr);
        } catch (NumberFormatException | DateTimeParseException nfe) {
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
