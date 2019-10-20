package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a place associated with a log entry in the finance log.
 */
public class Place {

    public static final String MESSAGE_CONSTRAINTS = "Places can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Place}.
     *
     * @param place A valid place.
     */
    public Place(String place) {
        requireNonNull(place);
        checkArgument(isValidPlace(place), MESSAGE_CONSTRAINTS);
        value = place;
    }

    /**
     * Returns true if a given string is a valid place.
     */
    public static boolean isValidPlace(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Place // instanceof handles nulls
                && value.equals(((Place) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
