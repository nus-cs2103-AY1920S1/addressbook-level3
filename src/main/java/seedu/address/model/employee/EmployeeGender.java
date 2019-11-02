package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)} (String)}
 */
public class EmployeeGender {

    public static final String MESSAGE_CONSTRAINTS =
            "Gender should be stated as strictly either 'male' or 'female' (Case insensitive), "
                    + "and it should not be blank";

    /*
     * The first character of the gender must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String gender;

    /**
     * Constructs a {@code EmployeeName}.
     *
     * @param gender A valid gender.
     */
    public EmployeeGender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.gender = gender;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        test = test.toLowerCase();
        if (test.equals("male") || test.equals("female")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeGender // instanceof handles nulls
                && gender.equals(((EmployeeGender) other).gender)); // state check
    }
}

