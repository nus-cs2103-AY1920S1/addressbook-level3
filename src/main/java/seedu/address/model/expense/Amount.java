package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the amount of an expense in the MYMorise.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS = "Amount should should not be blank, may"
        + " contain only numbers, up to 14 digits and 2 decimal places. No currency prefix is needed and"
        + " amount cannot be 0 or negative";
    public static final String VALIDATION_REGEX = "[\\d]{1,12}[.]??[\\d]{0,2}";

    public final String value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = convertToTwoDecimal(amount);
    }

    /**
     * Returns if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX) && Double.parseDouble(test) != 0.0;
    }

    public double getValue() {
        return Double.parseDouble(value.replaceAll("[^\\d.]", ""));
    }

    public String convertToTwoDecimal(String value) {
        return String.format("%.2f", Double.parseDouble(value));
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
