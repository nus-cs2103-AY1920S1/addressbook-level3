package seedu.address.model.calendar.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the date when the Task occurs.
 */
public class TaskDeadline {

    public static final String MESSAGE_CONSTRAINTS = "Deadline should be in the format dd/mm/yyyy";

    private String value;

    /**
     * Constructs a {@code TaskTime}.
     *
     * @param date A valid phone number.
     */
    public TaskDeadline(String date) {
        boolean isValidDate = isValidDate(date);
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
    public static boolean isValidDate(String test) {
        DateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
        try {
            parser.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
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
