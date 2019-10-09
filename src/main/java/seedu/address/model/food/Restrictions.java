package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's category in the address book.
 */
public class Restrictions {

    public static final String MESSAGE_CONSTRAINTS =
            "Restrictions should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String DEFAULT_VALUE = "No Restrictions";
    /*
     * The first character of the restrictions must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String restrictions;

    /**
     * Constructs a {@code Restrictions}
     *
     * @param restrictions A valid restrictions.
     */
    public Restrictions(String restrictions) {
        requireNonNull(restrictions);
        checkArgument(isValidRestrictions(restrictions), MESSAGE_CONSTRAINTS);
        this.restrictions = restrictions;
    }

    public static boolean isValidRestrictions(String restrictions) {
        return restrictions.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return restrictions;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if object is the same.
                || (other instanceof Restrictions // instanceof handles nulls.
                && restrictions.equals(((Restrictions) other).restrictions));  // State check.
    }

    @Override
    public int hashCode() {
        return restrictions.hashCode();
    }
}
