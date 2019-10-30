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
                    + "For example: 1.50 or 200";
    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";
    public final BigDecimal value;

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

    public boolean isValidMoney(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public BigDecimal getAmount() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("$%.02f", getAmount());
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
