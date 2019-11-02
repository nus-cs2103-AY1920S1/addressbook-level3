package seedu.address.model.calendar.task;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the date when the Task occurs.
 */
public class TaskDeadline {

    public static final String MESSAGE_CONSTRAINTS = "Deadline should be in the format dd-mm-yyyy. "
        + "The date must exist (e.g. 31-02-2019 is not a valid deadline)";

    private String value;

    /**
     * Constructs a {@code TaskDay}.
     *
     * @param date A valid phone number.
     */
    public TaskDeadline(String date) {
        boolean isValidDate = isValidDeadline(date);
        checkArgument(isValidDate, MESSAGE_CONSTRAINTS);
        value = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDeadline(String test) {
        DateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
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
                || (other instanceof TaskDeadline // instanceof handles nulls
                && value.equals(((TaskDeadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
