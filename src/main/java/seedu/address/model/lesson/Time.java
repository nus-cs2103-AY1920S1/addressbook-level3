package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a class time in the Classroom.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "There is no such date / time! Input should be in dd/MM/yyyy HHmm format.";
    public static final String MESSAGE_TIME_CONSTRAINT =
            "Time inputted should not be before the current system time.";
    public static final String VALIDATION_REGEX = "[\\d]{1,2}" + "/" + "[\\d]{1,2}" + "/" + "[\\d]{2,4}"
            + " " + "[\\d]{4}";

    private final Calendar time;

    public Time(Calendar time) {
        requireNonNull(time);
        this.time = time;
    }

    /** Gets the time in Calendar format. */
    public Calendar getTime() {
        return this.time;
    }

    /** Gets the day in String format. */
    public String getStringDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        return formatter.format(time.getTime());
    }

    /** Gets the time in String format. */
    public String getStringTime() {
        return this.toString();
    }

    /** Gets the time in Json format. */
    public String getJsonStringTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        return formatter.format(time.getTime());
    }

    /** Returns if a given string is a valid time. */
    public static boolean isValidTime(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy h:mm a");
        return formatter.format(time.getTime());
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }
}
