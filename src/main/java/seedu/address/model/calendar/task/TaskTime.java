package seedu.address.model.calendar.task;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Task's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in format HH:mm in 24-hour format, e.g. 13:45";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code TaskTime}.
     *
     * @param address A valid address.
     */
    public TaskTime(String address) {
        // requireNonNull(address);
        checkArgument(isValidTime(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidTime(String test) {
        DateFormat parser = new SimpleDateFormat("HH:mm");
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
