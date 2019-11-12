package seedu.ezwatchlist.model.show;

import static java.util.Objects.isNull;

/**
 * Represents a Show's date of release in the watchlist.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Dates can take any values, and it should not be blank";
    public static final String DEFAULT_VALUE = "?";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public Date() {
        value = DEFAULT_VALUE;
    }

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        //instead of having constraints perhaps could check if the object is null in the creation
        if (isNull(date) || date.equals("")) {
            date = DEFAULT_VALUE;
        }
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
