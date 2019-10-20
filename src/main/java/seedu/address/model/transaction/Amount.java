package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Amount in terms of cents
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts should only be in integer or double, and it should not be blank";

    public static final String DOUBLE_CONSTRAINTS =
            "Doubles passed into Amount constructor should have maximum 2 decimal places";

    public static final String SHARE_CONSTRAINTS =
            "Shares cannot be negative";

    private int amount;

    public Amount(double amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), DOUBLE_CONSTRAINTS);
        this.amount = (int) Math.floor(amount * 100);
    }

    public Amount(int amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Returns true if a given value is a valid amount.
     */
    public static boolean isValidAmount(double amount) {
        return (amount * 100) % 1 < 2 * Double.MIN_VALUE;
    }

    /**
     * Returns amount in cents
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Adds this.amount by amount.
     * @param amount Amount to be added.
     * @return New amount after addition.
     */
    public Amount addAmount(Amount amount) {
        final int newAmount = this.amount + amount.amount;
        return new Amount(newAmount);
    }

    /**
     * Subtracts this.amount by amount.
     * @param amount Amount to be subtracted.
     * @return New amount after subtraction.
     */
    public Amount subtractAmount(Amount amount) {
        final int newAmount = this.amount - amount.amount;
        return new Amount(newAmount);
    }

    /**
     * Multiplies amount by some fraction.
     * @param portion Fraction of Amount.
     * @return
     */
    public Amount byShare(double portion) {
        checkArgument(portion >= 0, SHARE_CONSTRAINTS);
        double newAmount = this.amount * portion;
        return new Amount((int) newAmount);
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount / 100.0);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof Amount
                && amount == ((Amount) obj).amount);
    }
}
