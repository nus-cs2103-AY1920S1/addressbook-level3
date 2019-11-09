package seedu.address.model.activity;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expense Amount in an Expense.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(double)}
 */
public class Amount {
    public static final double MIN = 0.01;
    public static final double MAX = 1000000;

    public static final String MESSAGE_CONSTRAINTS =
            "The amount of money spent (after rounding to 2 decimal places) can only be from $"
            + MIN + " to $" + MAX + ".";
    public static final String MESSAGE_POSITIVE_ONLY =
            "Amounts of money have to be positive!";

    public final double value;

    /**
     * Constructs an {@code Amount}.
     * Only non-negative amounts are acceptable.
     *
     * @param amount A valid amount of money spent.
     */
    public Amount(double amount) {
        checkArgument(amount >= 0, MESSAGE_POSITIVE_ONLY);
        value = amount;
    }

    /**
     * Returns true if the given amount is a valid amount for an expense.
     */
    public static boolean isValidAmount(double test) {
        double d = Math.round(test * 100) / 100.0;
        return d > 0 && d <= MAX;
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
