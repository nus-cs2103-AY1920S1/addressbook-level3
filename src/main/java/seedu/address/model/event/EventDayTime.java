package seedu.address.model.event;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents the time period of an EventDate. Contains a start time and an end time.
 */
public class EventDayTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Time Period should be in the following format: HHMM-HHMM, StartingTime < EndingTime";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private final LocalTime startTime;
    private final LocalTime endTime;

    public EventDayTime(LocalTime startTime, LocalTime endTime) {
        assert endTime.isAfter(startTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Default EventDayTime to be initalized. Represents 8am-6pm.
     */
    public static EventDayTime defaultEventDayTime() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        return new EventDayTime(startTime, endTime);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Shows the number of minutes within this Time period
     *
     * @return long number
     */
    public long numMinutes() {
        return Duration.between(startTime, endTime).toMinutes(); //end-exclusive
    }

    /**
     * Return true if given string represents a valid time period within a day.
     *
     * @param test E.g. "1020-1900"
     * @return boolean
     */
    public static boolean isValidTime(String test) {
        try {
            String[] timeSplit = test.split("-");
            LocalTime start = LocalTime.parse(timeSplit[0], FORMATTER);
            LocalTime end = LocalTime.parse(timeSplit[1], FORMATTER);
            return start instanceof LocalTime && end instanceof LocalTime
                    && end.isAfter(start);
        } catch (DateTimeException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return startTime.format(FORMATTER) + "-" + endTime.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDayTime // instanceof handles nulls
                && startTime.equals(((EventDayTime) other).getStartTime()) // state check)
                && endTime.equals(((EventDayTime) other).getEndTime())
            );
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
