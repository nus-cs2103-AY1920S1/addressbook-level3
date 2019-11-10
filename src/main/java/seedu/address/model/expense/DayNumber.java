package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Generic abstraction of day number.
 */
public class DayNumber {
    public static final String MESSAGE_CONSTRAINTS = "Day number can take any positive integer "
            + "and it should not be blank";

    /*
     * The first character of the dayNumber must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    public final String value;

    /**
     * Constructs an {@code DayNumber}.
     *
     * @param value A valid dayNumber.
     */
    public DayNumber(String value) {
        requireNonNull(value);
        checkArgument(isValidDayNumber(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid day number.
     */
    public static boolean isValidDayNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getValue() {
        return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DayNumber // instanceof handles nulls
                && value.equals(((DayNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
