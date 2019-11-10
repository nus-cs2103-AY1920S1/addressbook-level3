package seedu.moolah.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.AppUtil.checkArgument;

/**
 * Represents a Expense's price in the MooLah.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {


    public static final String MESSAGE_CONSTRAINTS =
            "Prices can only be positive numbers, with cents being separated from dollars with a '.' and "
                    + "a maximum of 2 decimal places allowed.";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{0,2})?$";
    public static final String ZERO = "0";
    public static final Price MAX_PRICE = new Price("1000000000000000000000");

    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX) && !test.equals(ZERO);
    }

    public Double getAsDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return String.format("%.2f", getAsDouble());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
