package seedu.tarence.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's matriculation number.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatric(String)}
 */

public class MatricNum {

    public static final String MESSAGE_CONSTRAINTS = "Matriculation numbers should be of the format [AU]*******[A-Z] "
            + "and adhere to the following constraints:\n"
            + "1. The matriculation number should start with an `A` or `U` and should end with a letter [A-Z].\n"
            + "2. There should be 7 numbers [0-9] between the first and last characters";
    // alphanumeric and special characters
    public static final String VALIDATION_REGEX = "^[AU][0-9]{7}[A-Z]$";

    public final String value;

    /**
     * Constructs an {@code MatricNum}.
     *
     * @param matricNum A valid matriculation number.
     */
    public MatricNum(String matricNum) {
        requireNonNull(matricNum);
        checkArgument(isValidMatricNum(matricNum.toUpperCase()), MESSAGE_CONSTRAINTS);
        value = matricNum.toUpperCase();
    }

    /**
     * Returns if a given string is a valid matric.
     */
    public static boolean isValidMatricNum(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNum // instanceof handles nulls
                && value.equalsIgnoreCase(((MatricNum) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
