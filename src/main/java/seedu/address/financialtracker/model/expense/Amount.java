package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

/**
 * A finance expense.
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain numbers with maximum two decimals";
    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    public final String value;
    public final double numericalValue;

    public Amount(String amount) {
        requireNonNull(amount);
        this.value = amount;
        this.numericalValue = Double.parseDouble(amount);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

}
