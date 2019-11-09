package seedu.ichifund.model.amount;

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
                    + "<dollars> consists of numbers up to 5 digits with no leading zeroes, unless it is '0'.\n"
                    + "<cents> consists of numbers, and is exactly 2 digits long.";
    public static final String POSITIVE_AMOUNT_CONSTRAINT =
            "Amount should be positive for transactions, repeatables, budgets, and loan.\n";
    public static final String CENTS_REGEX = "(\\.\\d\\d)"; // '.' followed by exactly two numerical digits
    public static final String DOLLARS_REGEX = "\\-?([1-9]\\d{0,4}|0)"; // '0', or number without leading zeroes
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
        String[] amounts = amount.split("\\.");
        if (amounts.length == 1) { // String contains only dollar and no cents
            valueInCents = Integer.parseInt(amount) * 100;
        } else { // String contains only cents
            valueInCents = getValueFromDollarAndCents(amounts[0], amounts[1]);
        }
    }

    private Amount(int valueInCents) {
        this.valueInCents = valueInCents;
    }

    /**
     * Returns integer representing value from a {@code String dollar} and a {@code String cents}
     *
     * @param dollar String representing value in dollars, can have negative sign.
     * @param cents String representing value in cents.
     * @return Combined value in cents
     */
    private static int getValueFromDollarAndCents(String dollar, String cents) {
        int dollarValue = Integer.parseInt(dollar);
        int centsValue = Integer.parseInt(cents);
        if (dollar.charAt(0) == '-') {
            return dollarValue * 100 - centsValue;
        } else {
            return dollarValue * 100 + centsValue;
        }
    }


    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a negative amount.
     * String must first correspond to a valid amount.
     */
    public static boolean isNegative(String test) {
        return test.substring(0, 1).equals("-");
    }

    /**
     * Returns true if a given string is zero.
     * String must first correspond to a valid amount.
     */
    public static boolean isZero(String test) {
        return test.equals("0") || test.equals("0.00");
    }

    @Override
    public String toString() {
        int dollars = valueInCents / 100;
        int cents = java.lang.Math.abs(valueInCents) % 100; // Absolute value required due to behaviour of %
        String centsString = convertCentsToString(cents);

        if (dollars == 0 && valueInCents < 0) {
            return "-0." + centsString;
        } else {
            return dollars + "." + centsString;
        }
    }

    /**
     * Converts a value in cents to a two character {@code String}.
     * Precondition: The input must be positive and less than 100.
     *
     * @param cents The number of cents in an {@code Amount} that is less than 100.
     * @return A two character {@code String}.
     */
    private static String convertCentsToString(int cents) {
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
    public static Amount addAll(List<Amount> amounts) {
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
    public static Amount add(Amount operand1, Amount operand2) {
        return new Amount(operand1.getValueInCents() + operand2.getValueInCents());
    }

    /**
     * Returns an {@code Amount} object obtained from the subtraction of two {@code Amount} objects.
     *
     * @param operand1 First operand to be subtracted.
     * @param operand2 Second operand to be subtracted from first operand.
     * @return An {@code Amount} obtained from subtraction.
     */
    public static Amount subtract(Amount operand1, Amount operand2) {
        return new Amount(operand1.getValueInCents() - operand2.getValueInCents());
    }

    /**
     * Returns the negative of an {@code Amount}.
     *
     * @param amount {@code Amount} object to be negated.
     * @return The negative of an {@code Amount}.
     */
    public static Amount negate(Amount amount) {
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

