package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Description of goods.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description allows all types of character and should not be over 255 characters in length.";

    public static final String VALIDATION_REGEX = "^.{1,255}$";
    public final String value;

    /**
     * Constructs a valid description.
     *
     * @param description A valid Goods description.
     */

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is valid .
     */

    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Description that = (Description) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
