package seedu.address.model.phone;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's quantity in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(long)}
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS = "Quantities must be non-negative.";

    public final long value;

    /**
     * Constructs a {@Code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(long quantity) {
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given long is a valid quantity.
     */
    public static boolean isValidQuantity(long quantity) {
        return quantity >= 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value == ((Quantity) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Long.valueOf(value).hashCode();
    }

}
