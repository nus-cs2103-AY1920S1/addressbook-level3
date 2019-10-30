package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents a class time in the Classroom.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "There is no such date / time! Input should be in dd/MM/yyyy HHmm format.";
    public static final String MESSAGE_TIME_CONSTRAINT =
            "Time stated should not be before current time.";

    private final Calendar time;

    public Time(Calendar time) {
        requireNonNull(time);
        this.time = time;
    }

    public Calendar getTime() {
        return this.time;
    }

    public Date getTimeToCompare() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy h:mm a");
        Date date = null;
        try {
            date = formatter.parse(getStringTime());
        } catch (ParseException e) {
            e.getMessage();
        }
        return date;
    }

    public String getStringTime() {
        return this.toString();
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
