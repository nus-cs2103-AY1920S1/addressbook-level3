package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's Pay  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPay(String)}
 */
public class EmployeePay {

    public static final String MESSAGE_CONSTRAINTS =
            "EmployeePay should only contain numbers, and it should be at least 1 digit and at most 2 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,2}";
    public final double value;


    /**
     * Constructs a {@code EmployeeSalaryPaid}.
     *
     * @param pay A valid Pay number.
     */
    public EmployeePay(String pay) {
        requireNonNull(pay);
        checkArgument(isValidPay(pay), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(pay);
    }

    public double getPay() {
        return value;
    }

    /**
     * Returns true if a given string is a valid Pay number.
     */
    public static boolean isValidPay(String test) {
        try {
            double value = Double.parseDouble(test);
            return value < 100 && value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePay // instanceof handles nulls
                && value == ((EmployeePay) other).getPay()); // state check
    }

}

