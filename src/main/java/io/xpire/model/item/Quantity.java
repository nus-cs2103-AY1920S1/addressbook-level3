package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.parser.exceptions.ParseException;

/**
 * Represents the quantity of an item.
 * Guarantees: immutable and valid in {@link #isValidQuantity(String test)}.
 */
public class Quantity {
    public static final String MESSAGE_CONSTRAINTS =
            "Quantity should be a positive integer and should not be blank";
    public static final String DEFAULT_QUANTITY = "1";
    private int quantity;

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

    /**
     * Returns true if quantity is zero.
     */
    public static boolean quantityIsZero(Quantity quantity) {
        return quantity.quantity <= 0;
    }

    /**
     * Reduces quantity by specified amount.
     *
     * @param deductAmount Amount to be deducted.
     * @return Quantity to be deducted.
     * @throws ParseException if new quantity is negative.
     */
    public Quantity deductQuantity(Quantity deductAmount) throws ParseException {
        if (this.quantity < deductAmount.quantity) {
            throw new ParseException("Quantity should not be negative!");
        }
        this.quantity = this.quantity - deductAmount.quantity;
        return this;
    }

    @Override
    public String toString() {
        return "" + this.quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Quantity)) {
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
