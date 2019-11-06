package seedu.address.model.activity;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expense Amount in an Expense.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(double)}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "The amount of money spent (after rounding to 2 decimal places) can only be from $0.01 to $1000000";

    public static final double MIN = 0.01;
    public static final double MAX = 1000000;

    public final double value;

    /**
     * Constructs an {@code Amount}.
     * If the given amount is less than 0.01 but rounds to 0.01, it will be stored as 0.01.
     * Likewise if the given amount is more than 1 million but rounds to 1 million, it will be stored as 1 million.
     *
     * @param amount A valid amount of money spent.
     */
    public Amount(double amount) {
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        if (amount > MAX) {
            value = MAX;
        } else if (amount > 0 && amount < MIN) { //TODO: verify that this does not conflict with settlement when merged
            value = MIN;
        } else {
            value = amount;
        }
    }

    /**
     * Returns true if the given amount is a valid amount.
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
