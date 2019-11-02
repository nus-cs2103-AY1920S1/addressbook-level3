package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class EmployeePhone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at "
                    + "between 7-15 digits long, according to the E.164 ITU-T Recommendation.\n"
                    + "All phone numbers are assumed with the +65 extension, so it is not required to be stated.";
    public static final String VALIDATION_REGEX = "\\d{7,15}";
    public final String value;

    /**
     * Constructs a {@code EmployeePhone}.
     *
     * @param phone A valid phone number.
     */
    public EmployeePhone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePhone // instanceof handles nulls
                && value.equals(((EmployeePhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
