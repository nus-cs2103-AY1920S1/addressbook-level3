package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

/**
 * A finance expense.
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain numbers";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    public Amount(String amount) {
        requireNonNull(amount);
        this.value = amount;
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
