package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Amount in terms of cents
 */
public class Amount implements Comparable<Amount> {

    public static final String MESSAGE_CONSTRAINTS =
        "Amounts should only be in integer or double, and it should not be blank";

    public static final String DOUBLE_CONSTRAINTS =
        "Doubles passed into Amount constructor should have maximum 2 decimal places";

    public static final String INT_CONSTRAINTS =
        "Amount should not exceed $10,000,000. May result in overflow!";

    public static final String SHARE_CONSTRAINTS =
        "Shares cannot be negative";

    private int amount;

    public Amount(double amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), DOUBLE_CONSTRAINTS);
        int cents = (int) Math.floor(amount * 100);
        checkArgument(isWithinLimits(cents), INT_CONSTRAINTS);
        this.amount = cents;
    }

    /**
     * Creates Amount object of {@code amount} cents
     * @param amount amount in cents
     */
    public Amount(int amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        checkArgument(isWithinLimits(amount), INT_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Returns true if a given value has maximally 2 decimal points.
     */
    public static boolean isValidAmount(double amount) {
        return (amount * 100) % 1 < 2 * Double.MIN_VALUE;
    }

    public static boolean isWithinLimits(int amount) {
        return amount < Integer.MAX_VALUE / 2;
    }

    /**
     * Returns amount in cents.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Sets amount in cents.
     */
    public void setAmount(Amount amount) {
        this.amount = amount.amount;
    }

    /**
     * Adds this.amount by amount.
     *
     * @param amount Amount to be added.
     * @return New amount after addition.
     */
    public Amount addAmount(Amount amount) {
        final int newAmount = this.amount + amount.amount;
        return new Amount(newAmount);
    }

    /**
     * Subtracts this.amount by amount.
     *
     * @param amount Amount to be subtracted.
     * @return New amount after subtraction.
     */
    public Amount subtractAmount(Amount amount) {
        final int newAmount = this.amount - amount.amount;
        return new Amount(newAmount);
    }

    /**
     * Multiplies amount by some fraction.
     *
     * @param portion Fraction of Amount.
     * @return
     */
    public Amount byShare(double portion) {
        checkArgument(portion >= 0, SHARE_CONSTRAINTS);
        double newAmount = this.amount * portion;
        return new Amount((int) newAmount);
    }

    /**
     * Make amount negative.
     *
     * @param
     * @return negative amount
     */
    public Amount makeNegative() {
        final int newAmount = this.amount * -1;
        return new Amount(newAmount);
    }

    public boolean isNegative() {
        return this.amount < 0;
    }

    /**
     * Create new Amount of 0
     *
     * @return Amount of 0
     */
    public static Amount zero() {
        return new Amount(0);
    }

    public static Amount of(int i) {
        return new Amount(i * 100);
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

    @Override
    public int compareTo(Amount amount) {
        if (this.amount == amount.amount) {
            return 0;
        } else if (this.amount > amount.amount) {
            return 1;
        } else {
            return -1;
        }
    }
}
