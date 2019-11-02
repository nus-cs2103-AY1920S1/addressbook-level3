package seedu.address.model.employee;

/**
 * Represents a Employee's Pay  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPay(String)}
 */
public class EmployeeTotalSalary {

    public static final String MESSAGE_CONSTRAINTS =
            "EmployeePay numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public String value;
    //private final int value; <--Should be using this instead!

    public EmployeeTotalSalary() {
        //value = 0;
    }

    /**
     * Constructs a {@code EmployeePay}.
     *
     * @param pay A valid Pay number.
     */
    public EmployeeTotalSalary(String pay) {
        value = pay;
    }

    /**
     * Returns true if a given string is a valid Pay number.
     */
    public static boolean isValidPay(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeTotalSalary // instanceof handles nulls
                && value.equals(((EmployeeTotalSalary) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

