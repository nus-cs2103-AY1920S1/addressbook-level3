package seedu.address.model;

import java.time.Duration;
import java.util.Objects;

/**
 * Represents a time duration.
 */
public class TimeDuration {
    public static final String MESSAGE_CONSTRAINTS = "Time duration must be in the format: hh:mm and it should not be" +
            "blank";

    public static final String VALIDATION_REGEX = "^\\d{2}:\\d{2}$";

    private int hours;
    private int minutes;

    public TimeDuration(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    /**
     * Returns true if a given string is a valid time duration;
     */
    public static boolean isValidTimeDuration(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%d hours %d minutes", hours, minutes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeDuration) // instanceof handles nulls
                && this.hours == ((TimeDuration) other).hours
                && this.minutes == ((TimeDuration) other).hours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }
}
