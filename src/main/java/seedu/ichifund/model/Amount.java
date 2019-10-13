package seedu.ichifund.model;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

import java.util.List;

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

    private Amount(int valueInCents) {
        this.valueInCents = valueInCents;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (valueInCents < 0) {
            return "-" + (new Amount(-valueInCents)).toString();
        } else {
            int dollars = valueInCents / 100;
            int cents = valueInCents % 100;
            String centsString = convertCentsToString(cents);
            return "$" + dollars + "." + centsString;
        }
    }

    /**
     * Converts a value in cents to a two character {@code String}.
     * Precondition: The input must be less than 100.
     *
     * @param cents The number of cents in an {@code Amount} that is less than 100.
     * @return A two character {@code String}.
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

    /**
     * Returns an {@code Amount} object obtained from the summation a {@code List} of {@code Amount} objects.
     *
     * @param amounts List of {@code Amount} to be added.
     * @return An {@code Amount} obtained from summation.
     */
    public Amount addAll(List<Amount> amounts) {
        int valueInCents = 0;
        for (Amount amount : amounts) {
            valueInCents += amount.getValueInCents();
        }
        return new Amount(valueInCents);
    }

    /**
     * Returns an {@code Amount} object obtained from adding two {@code Amount} objects.
     *
     * @param operand1 First operand to be added.
     * @param operand2 Second operand to be added.
     * @return An {@code Amount} obtained from addition.
     */
    public Amount add(Amount operand1, Amount operand2) {
        return new Amount(operand1.getValueInCents() + operand2.getValueInCents());
    }

    /**
     * Returns an {@code Amount} object obtained from the subtraction of two {@code Amount} objects.
     *
     * @param operand1 First operand to be subtracted.
     * @param operand2 Second operand to be subtracted from first operand.
     * @return An {@code Amount} obtained from subtraction.
     */
    public Amount subtract(Amount operand1, Amount operand2) {
        return new Amount(operand1.getValueInCents() - operand2.getValueInCents());
    }

    /**
     * Returns the negative of an {@code Amount}.
     *
     * @param amount {@code Amount} object to be negated.
     * @return The negative of an {@code Amount}.
     */
    public Amount negate(Amount amount) {
        return new Amount(-amount.getValueInCents());
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

