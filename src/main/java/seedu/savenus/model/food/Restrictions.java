package seedu.savenus.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's category in the address book.
 */
public class Restrictions implements Field {

    public static final String MESSAGE_CONSTRAINTS =
            "Restrictions should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String DEFAULT_VALUE = "No Restrictions";

    // Restrictions cannot be blank but can contain any other characters
    public static final String VALIDATION_REGEX = ".*\\S.*";

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

    /**
     * Gets the field as a String.
     * @return a String representation of the field.
     */
    public String getField() {
        return this.toString();
    }

    @Override
    public String toString() {
        return restrictions;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if object is the same.
                || (other instanceof Restrictions // instanceof handles nulls.
                && restrictions.equals(((Restrictions) other).restrictions)); // State check.
    }

    @Override
    public int hashCode() {
        return restrictions.hashCode();
    }

    @Override
    public int compareTo(Field other) {
        Restrictions otherRestrictions = (Restrictions) other;
        if (otherRestrictions == null) {
            return 1;
        } else {
            return this.getField().compareTo(otherRestrictions.getField());
        }
    }
}
