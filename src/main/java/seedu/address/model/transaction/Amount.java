package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's amount in the transaction recorder.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {


    public static final String MESSAGE_CONSTRAINTS =
            "Amount should be in the format '<dollars>.<cents>' or '<dollars>'.\n" +
                    "<dollars> consists of numbers with no leading zeroes, unless it is '0'.\n" +
                    "<cents> consists of numbers, and is exactly 2 digits long.";
    public static final String CENTS_REGEX = "(\\.\\d\\d)"; // '.' followed by exactly two numerical digits
    public static final String DOLLARS_REGEX =  "([1-9]\\d*|0)"; // '0', or number without leading zeroes
    public static final String VALIDATION_REGEX = DOLLARS_REGEX + CENTS_REGEX + "?"; // Dollars, with cents optionally
    public final String value;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = amount;
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && value.equals(((Amount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

