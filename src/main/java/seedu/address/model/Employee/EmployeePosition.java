package seedu.address.model.Employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's position in the AddMin+.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class EmployeePosition {

    public static final String MESSAGE_CONSTRAINTS =
            "Position should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullPosition;

    /**
     * Constructs a {@code EmployeePosition}.
     *
     * @param position A valid position.
     */
    public EmployeePosition(String position) {
        requireNonNull(position);
        checkArgument(isValidPosition(position), MESSAGE_CONSTRAINTS);
        fullPosition = position;
    }

    /**
     * Returns true if a given string is a valid position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullPosition;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePosition // instanceof handles nulls
                && fullPosition.equals(((EmployeePosition) other).fullPosition)); // state check
    }

    @Override
    public int hashCode() {
        return fullPosition.hashCode();
    }

}

