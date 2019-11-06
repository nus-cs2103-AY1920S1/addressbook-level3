package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutor's earnings.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount numbers should only contain positive numbers with only 2 decimal points,"
                    + " and it must be lesser than 1 000 000.00";
    public static final String VALIDATION_REGEX = "^\\d{1,3}(,?\\d{3})?(\\.\\d{2})?$";
    private static final double MAX_VALUE = 1000000.00;

    public final String amount;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amt A valid amount.
     */
    public Amount(String amt) {
        requireNonNull(amt);
        checkArgument(isValidAmount(amt), MESSAGE_CONSTRAINTS);
        amount = amt;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if amount is more than max value.
     */
    public static boolean moreThanMaxValue(String amount) {
        double amt = Double.parseDouble(amount);
        return amt >= MAX_VALUE;
    }

    @Override
    public String toString() {
        return amount;
    }

    public Double doubleValue() {
        return Double.parseDouble(amount);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount.equals(((Amount) other).amount)); // state check
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

}
