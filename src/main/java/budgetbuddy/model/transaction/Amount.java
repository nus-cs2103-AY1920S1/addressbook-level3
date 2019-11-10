package budgetbuddy.model.transaction;

import budgetbuddy.commons.util.AppUtil;

/**
 * Represents the Amount in a Transaction.
 * Guarantees: immutable, is valid as declared in {@link #isValidAmount(long)}
 */
public class Amount implements Comparable<Amount> {

    public static final String CURRENCY_SIGN = "$";
    public static final String MAX_AMOUNT = "9999999999999999";

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts should be non-negative numbers, should not be blank, "
                    + "and should not exceed " + CURRENCY_SIGN + MAX_AMOUNT + ".";
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

    public boolean lessThanEquals(Amount amount) {
        return this.amount <= amount.amount;
    }

    public boolean moreThanEquals(Amount amount) {
        return this.amount >= amount.amount;
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
    public int compareTo(Amount other) {
        return Long.compare(amount, other.amount);
    }

    @Override
    public String toString() {
        return String.format("%s%d.%02d", CURRENCY_SIGN, amount / 100, amount % 100);
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
