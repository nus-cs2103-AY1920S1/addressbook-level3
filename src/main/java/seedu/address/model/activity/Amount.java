package seedu.address.model.activity;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expense Amount in an Expense.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(double)}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "The amount of money spent can only be a positive number from 0.01 to 1000000, "
            + "specified up to 2 decimal places";

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
     * Returns true if the given amount is a valid amount.
     */
    public static boolean isValidAmount(double test) {
        return test > 0 && test <= 1000000 && test * 100 == (int) (test * 100);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Amount) {
            return Math.abs(value - ((Amount) other).value) < 1e-10;
        } else {
            return false;
        }
    }
}
