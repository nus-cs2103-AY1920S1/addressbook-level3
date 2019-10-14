package seedu.address.model.Employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's ID number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidID(String)}
 */
public class EmployeeID {


    public static final String MESSAGE_CONSTRAINTS =
            "EmployeeID should only contain numbers, and it should be at least 1 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code EmployeeID}.
     *
     * @param ID A valid ID number.
     */
    public EmployeeID(String ID) {
        requireNonNull(ID);
        checkArgument(isValidID(ID), MESSAGE_CONSTRAINTS);
        value = ID;
    }

    /**
     * Returns true if a given string is a valid ID number.
     */
    public static boolean isValidID(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeID // instanceof handles nulls
                && value.equals(((EmployeeID) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

