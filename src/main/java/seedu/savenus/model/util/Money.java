package seedu.savenus.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

/**
 * Model for representing monetary amounts
 */
public class Money implements Comparable<Money> {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain numbers and have either 0 or 2 decimal places"
                    + " For example: 1.50 or 200";
    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";

    private static final BigDecimal FLOATING_POINT_RESTRICTION = new BigDecimal(1000000.00);
    private static final BigDecimal ZERO_VALUE = BigDecimal.ZERO;

    private BigDecimal value;

    /**
     * Constructs a {@code Money}.
     *
     * @param amountString A amount represeting as a String.
     */
    public Money(String amountString) {
        requireNonNull(amountString);
        checkArgument(isValidMoney(amountString), MESSAGE_CONSTRAINTS);
        if (!amountString.contains(".")) {
            amountString += ".00";
        }
        this.value = new BigDecimal(amountString);
    }

    public Money(BigDecimal amount) {
        requireNonNull(amount);
        checkArgument(isValidMoney(amount.toString()), MESSAGE_CONSTRAINTS);
        this.value = amount;
    }

    public static boolean isValidMoney(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public BigDecimal getAmount() {
        return value;
    }

    /**
     * Checks if the money value is higher than the FLOATING_POINT_RESTRICTIONS
     */
    public boolean isOutOfBounds() {
        return this.value.compareTo(FLOATING_POINT_RESTRICTION) > 0;
    }

    /**
     * Check if the money value is negative
     */
    public boolean isNegativeValue() {
        return this.value.compareTo(ZERO_VALUE) < 0;
    }

    /**
     * Negate only for withdrawals
     */
    public void negate() {
        this.value = this.value.negate();
    }

    /**
     * Add 2 money value together.
     *
     * @param other the money to be added with this
     * @return a new Money object with the updated value
     */
    public Money add(Money other) {
        this.value = this.value.add(other.value);
        return new Money(this.value);
    }

    /**
     * Subtract a sum of money from this sum of money.
     * @param other the money to be subtracted from this money.
     * @return a new Money object with the updated value
     */
    public Money subtract(Money other) {
        this.value = this.value.subtract(other.value);
        return new Money(this.value);
    }

    @Override
    public String toString() {
        return String.format("%.02f", getAmount());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && value.equals(((Money) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Money other) {
        return value.compareTo(other.value);
    }
}
