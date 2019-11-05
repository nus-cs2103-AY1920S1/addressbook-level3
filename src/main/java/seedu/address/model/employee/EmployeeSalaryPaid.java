package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's Total Salary Paid in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalaryPaid(String)}
 */
public class EmployeeSalaryPaid {


    public static final String MESSAGE_CONSTRAINTS =
            "EmployeeSalaryPaid should be a positive integer value";

    private double value;

    /**
     * Constructs a {@code EmployeeSalaryPaid}.
     *
     * @param salaryPaid A valid SalaryPaid Amount
     */
    public EmployeeSalaryPaid(String salaryPaid) {
        requireNonNull(salaryPaid);
        checkArgument(isValidSalaryPaid(salaryPaid), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(salaryPaid);
    }

    public EmployeeSalaryPaid() {
        value = 0;
    }

    public double getValue() {
        return value;
    }

    public void add(double salaryPaid) {
        this.value += salaryPaid;
    }

    public void min(double salaryPaid) {
        this.value -= salaryPaid;
    }

    /**
     * Returns true if a given string is a valid Pay number.
     */
    public static boolean isValidSalaryPaid(String test) {
        try {
            return Double.parseDouble(test) < 100000;
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
                || (other instanceof EmployeeSalaryPaid // instanceof handles nulls
                && value == ((EmployeeSalaryPaid) other).value); // state check
    }

}

