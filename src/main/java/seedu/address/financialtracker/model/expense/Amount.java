package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

/**
 * An expense amount.
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain positive numbers with maximum two decimals\n"
            + "Please don't overspend :) Your total expenses are capped at 1 trillion. Fair enough right?";
    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";
    public final String value;
    public final double numericalValue;

    public Amount(String amount) {
        requireNonNull(amount);
        this.value = amountFormat(amount);
        this.numericalValue = Double.parseDouble(amount);
    }

    /**
     * Formats the amount string such that it always has tailing 2 decimal zeros.
     */
    public String amountFormat(String amount) {
        int dotIndex = amount.indexOf('.');
        if (dotIndex == -1) {
            amount += ".00";
        } else {
            if ((amount.length() - dotIndex) != 3) {
                amount += "0";
            }
        }
        while(amount.charAt(0) == '0') {
            amount = amount.substring(1);
        }
        return amount;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

}
