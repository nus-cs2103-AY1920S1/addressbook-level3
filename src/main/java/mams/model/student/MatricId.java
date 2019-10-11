package mams.model.student;

import static java.util.Objects.requireNonNull;
import static mams.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's MatricId in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricId(String)} (String)}
 */
public class MatricId {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the MatricId must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code MatricId}.
     *
     * @param matricId A valid MatricId.
     */
    public MatricId(String matricId) {
        requireNonNull(matricId);
        checkArgument(isValidMatricId(matricId), MESSAGE_CONSTRAINTS);
        value = matricId;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidMatricId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricId // instanceof handles nulls
                && value.equals(((MatricId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
