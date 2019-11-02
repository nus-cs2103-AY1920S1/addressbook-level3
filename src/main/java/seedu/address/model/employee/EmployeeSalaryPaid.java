package seedu.address.model.employee;


/**
 * Represents a Employee's Salary (per hour) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPay(String)}
 */
public class EmployeeSalaryPaid {


    public static final String MESSAGE_CONSTRAINTS =
            "EmployeeSalaryPaid should be a positive integer value";
    public static final String VALIDATION_REGEX = "\\d{1,}";

    private final int value;

    /**
     * Constructs a {@code EmployeeSalaryPaid}.
     *
     * @param value A valid Pay number.
     */
    public EmployeeSalaryPaid(int value) {
        this.value = value;
    }

    public EmployeeSalaryPaid() {
        value = 0;
    }

    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid Pay number.
     */
    public static boolean isValidPay(String test) {
        return test.matches(VALIDATION_REGEX);
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

