package seedu.address.model.phone;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's price in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(double)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS = "Prices must be non-negative.";

    public final double value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(double price) {
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = price;
    }

    /**
     * Returns true if a given double is a valid price.
     */
    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cost // instanceof handles nulls
                && value == ((Cost) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
