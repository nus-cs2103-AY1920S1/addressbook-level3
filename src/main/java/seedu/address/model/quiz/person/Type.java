package seedu.address.model.quiz.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's type in the modulo.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS = "Type value only accepts high, normal or low";
    public static final String VALIDATION_REGEX = "(high|normal|low)";

    public final String value;

    /**
     * Constructs an {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        value = type;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && value.equals(((Type) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
