package seedu.savenus.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location implements Field {

    public static final String MESSAGE_CONSTRAINTS = "Location should not be blank";
    public static final String DEFAULT_VALUE = "No location specfied";

    // Description cannot be blank but can contain any other characters
    public static final String VALIDATION_REGEX = ".*\\S.*";

    public final String location;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        this.location = location;
    }

    /**
     * Returns true if a given string is a valid categroy.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return location;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && location.equals(((Location) other).location)); // state check
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public int compareTo(Field other) {
        Location otherLocation = (Location) other;
        if (otherLocation == null) {
            return 1;
        } else {
            return this.getField().compareTo(otherLocation.getField());
        }
    }
}
