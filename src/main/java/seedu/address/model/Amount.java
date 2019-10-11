package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's amount in the transaction recorder.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount implements Comparable<Amount> {


    public static final String MESSAGE_CONSTRAINTS =
            "Amount should be in the format '<dollars>.<cents>' or '<dollars>'.\n"
                    + "<dollars> consists of numbers with no leading zeroes, unless it is '0'.\n"
                    + "<cents> consists of numbers, and is exactly 2 digits long.";
    public static final String CENTS_REGEX = "(\\.\\d\\d)"; // '.' followed by exactly two numerical digits
    public static final String DOLLARS_REGEX = "([1-9]\\d*|0)"; // '0', or number without leading zeroes
    public static final String VALIDATION_REGEX = DOLLARS_REGEX + CENTS_REGEX + "?"; // Dollars, with cents optionally
    public final int valueInCents;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        String[] amounts = amount.split(".");
        if (amounts.length == 1) { // String contains only dollar and no cents
            valueInCents = Integer.parseInt(amount) * 100;
        } else { // String contains only cents
            valueInCents = Integer.parseInt(amounts[0]) * 100 + Integer.parseInt(amounts[1]);
        }
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        int dollars = valueInCents / 100;
        int cents = valueInCents % 100;
        String centsString = convertCentsToString(cents);
        return "$" + dollars + "." + centsString;
    }

    /**
     * Converts a value in cents to a two character {@code String}.
     * Precondition: The input must be less than 100.
     *
     * @param cents The number of cents in an {@code Amount} that is less than 100.
     * @return A two character {@code String}
     */
    private String convertCentsToString(int cents) {
        if (cents == 0) {
            return "00";
        } else if (cents < 10) {
            return "0" + cents;
        } else {
            return "" + cents;
        }
    }

    public int getValueInCents() {
        return valueInCents;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && valueInCents == (((Amount) other).valueInCents)); // state check
    }

    @Override
    public int compareTo(Amount other) {
        return valueInCents - other.valueInCents;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}

