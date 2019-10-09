package seedu.address.model.activity;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expense Amount in an Expense.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(double)}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "The amount of money spent can only be a positive number";

    public final double value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount of money spent.
     */
    public Amount(double amount) {
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidAmount(double test) {
        return test > 0;
    }

    @Override
    public String toString() {
        return String.format("%f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && value == ((Amount) other).value); // state check
    }
}
