package seedu.address.model.event;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents the time period of an EventDate. Contains a start time and an end time.
 */
public class EventDayTime {
    public static final String MESSAGE_CONSTRAINTS = "Time should be in the following format HHMM";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private final LocalTime startTime;
    private final LocalTime endTime;

    public EventDayTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
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
            return LocalTime.parse(timeSplit[0], FORMATTER) instanceof LocalTime
                    && LocalTime.parse(timeSplit[1], FORMATTER) instanceof LocalTime;
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
