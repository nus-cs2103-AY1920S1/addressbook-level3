package io.xpire.model.item;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.StringUtil;

import static java.util.Objects.requireNonNull;

public class Quantity {
    public static final String MESSAGE_CONSTRAINTS =
            "Quantity should be a positive integer and should not be blank";

    private final int quantity;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        AppUtil.checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        this.quantity = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return StringUtil.isNonNegativeInteger(test);
    }

    @Override
    public String toString() {
        return "" + this.quantity;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ReminderThreshold)) {
            return false;
        } else {
            Quantity other = (Quantity) obj;
            return this.quantity == other.quantity;
        }
    }

    @Override
    public int hashCode() {
        return this.quantity;
    }
}
