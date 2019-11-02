package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Employee's Salary (per hour) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPay(String)}
 */
public class EmployeeSalaryPaid {


    public static final String MESSAGE_CONSTRAINTS =
            "EmployeeSalaryPaid numbers should only contain numbers";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;


    /**
     * Constructs a {@code EmployeeSalaryPaid}.
     *
     * @param pay A valid Pay number.
     */
    public EmployeeSalaryPaid(String pay) {
        requireNonNull(pay);
        value = pay;
    }

    /**
     * Returns true if a given string is a valid Pay number.
     */
    public static boolean isValidPay(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeSalaryPaid // instanceof handles nulls
                && value.equals(((EmployeeSalaryPaid) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

