package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Date;

/**
 * Represents a schedule's date in SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime can take any valid date and time, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*"; //TO CHANGE

    public final Date dateTime;

    /**
     * Constructs an {@code dateTime}.
     *
     * @param dateTime A valid Date object.
     */
    public DateTime(Date dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime.toString()), MESSAGE_CONSTRAINTS);
        this.dateTime = dateTime;
    }

    //To change VALIDATION_REGEX
    /**
     * Returns true if a given string is a valid order ID.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
