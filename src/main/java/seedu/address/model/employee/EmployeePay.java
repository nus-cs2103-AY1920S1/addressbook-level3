package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's pay in the AddMin+.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class EmployeePay {

    public static final String MESSAGE_CONSTRAINTS =
            "Position should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullPosition;

    /**
     * Constructs a {@code EmployeePay}.
     *
     * @param pay A valid pay.
     */
    public EmployeePay(String pay) {
        requireNonNull(pay);
        checkArgument(isValidPosition(pay), MESSAGE_CONSTRAINTS);
        fullPosition = pay;
    }

    /**
     * Returns true if a given string is a valid pay.
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
                || (other instanceof EmployeePay // instanceof handles nulls
                && fullPosition.equals(((EmployeePay) other).fullPosition)); // state check
    }

    @Override
    public int hashCode() {
        return fullPosition.hashCode();
    }

}

