package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Amount in terms of cents
 */
public class Amount implements Comparable<Amount> {

    public static final int UNSIGNED_INT_LIMIT = 1000000000;

    public static final String MESSAGE_CONSTRAINTS =
        "Amounts should only be in integer or double, and it should not be blank";

    public static final String DOUBLE_CONSTRAINTS =
        "Amount should have maximum 2 decimal places";

    public static final String INT_CONSTRAINTS =
        "Amount should not exceed $" + UNSIGNED_INT_LIMIT / 100 + ".\n";

    public static final String SHARE_CONSTRAINTS =
        "Shares cannot be negative";

    public static final String DIVIDE_CONSTRAINTS =
        "You cannot divide by $0";

    public static final DecimalFormat AMOUNT_DOUBLE_FORMAT = new DecimalFormat("#.00");

    private int amount;

    public Amount(double amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), DOUBLE_CONSTRAINTS);
        int cents = (int) Math.round(amount * 100);
        checkArgument(isWithinLimits(cents), INT_CONSTRAINTS);
        this.amount = cents;
    }

    /**
     * Creates Amount object of {@code amount} cents
     *
     * @param amount amount in cents
     */
    public Amount(int amount) {
        requireNonNull(amount);
        checkArgument(isWithinLimits(amount), INT_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Returns true if a given value has maximally 2 decimal points.
     */
    public static boolean isValidAmount(double amount) {
        // String amountStr = "" + amount;
        // int indexOfPeriod = amountStr.lastIndexOf(".");
        // return indexOfPeriod == -1
        //     || amountStr.substring(indexOfPeriod + 1).length() <= 2;
        return Math.abs(amount * 100 - Math.round(amount * 100)) < 2 * 1.13E-13;
    }

    public static double format(double value) {
        return Double.parseDouble(AMOUNT_DOUBLE_FORMAT.format(value));
    }

    /**
     * Checks if absolute value of {@code amount} is less than {@code MAX_AMOUNT}
     */
    public static boolean isWithinLimits(int cents) {
        return cents >= -UNSIGNED_INT_LIMIT && cents <= UNSIGNED_INT_LIMIT;
    }

    /**
     * Returns amount in cents.
     */
    public int getIntegerValue() {
        return this.amount;
    }

    /**
     * Returns amount in dollars
     */
    public double getActualValue() {
        return this.amount / 100.0;
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
     * divides this.amount by amount.
     *
     * @param amount Amount to be divided. It cannot be of ZERO value.
     * @return double after division.
     */
    public double divideAmount(Amount amount) {
        checkArgument(amount.amount != 0.00, DIVIDE_CONSTRAINTS);
        final double newAmount = (double) this.amount / amount.amount;
        return newAmount;
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
        if (amount < 0) {
            return new Amount(this.amount);
        }
        final int newAmount = this.amount * -1;
        return new Amount(newAmount);
    }

    /**
     * Make amount positive.
     *
     * @param
     * @return positive amount
     */
    public Amount makePositive() {
        if (amount >= 0) {
            return new Amount(this.amount);
        }
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
