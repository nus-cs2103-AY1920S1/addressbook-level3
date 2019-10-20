package budgetbuddy.model.transaction;

import budgetbuddy.commons.util.AppUtil;

/**
 * Represents the Amount in a Transaction.
 * Guarantees: immutable, is valid as declared in {@link #isValidAmount(long)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts should be non-negative numbers and should not be blank.";
    public static final String MESSAGE_CENTS_PARSE_ERROR =
            "Cents should be at most two decimal places long.";

    public static final String VALIDATION_REGEX = "^\\d+$";

    private final long amount;

    /**
     * Constructs an {@code Amount}
     *
     * @param amount a valid amount.
     */
    public Amount(long amount) {
        AppUtil.checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    public long toLong() {
        return amount;
    }

    /**
     * Returns true if a given long is a valid Amount.
     */
    public static boolean isValidAmount(long test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return String.format("%d.%02d", amount / 100, amount % 100);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount == ((Amount) other).amount);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(amount);
    }
}
