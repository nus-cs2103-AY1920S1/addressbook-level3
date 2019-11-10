package seedu.address.model.calendar.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Task's time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in format HH:mm in 24-hour format from "
        + "00:00 to 23:59";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code TaskTime}.
     *
     * @param address A valid address.
     */
    public TaskTime(String address) {
        requireNonNull(address);
        checkArgument(isValidTime(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidTime(String test) {
        DateFormat parser = new SimpleDateFormat("HH:mm");
        parser.setLenient(false);
        try {
            parser.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && value.equals(((TaskTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
