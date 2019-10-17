package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.parser.exceptions.ParseException;

/**
 * Represents the quantity of an item.
 * Users are only allowed to key in positive integers.
 * Internally, there can be quantity of value 0.
 * Guarantees: immutable and valid in {@link #isValidInputQuantity(String test)}.
 */
public class Quantity {

    public static final String DEFAULT_QUANTITY = "1";
    public static final String MESSAGE_CONSTRAINTS =
            "Quantity added should be a positive integer and should not be blank";
    private static final String INTERNAL_MESSAGE_CONSTRAINTS =
            "Quantity added should be a non-negative integer and should not be blank";
    private int quantity;


    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid input quantity, i.e. positive integer.
     */
    public Quantity(String quantity) {
        String trimmedQuantity = quantity.trim();
        requireNonNull(trimmedQuantity);
        AppUtil.checkArgument(isValidInputQuantity(trimmedQuantity), MESSAGE_CONSTRAINTS);
        this.quantity = Integer.parseInt(trimmedQuantity);
    }

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity, i.e. a non-negative integer.
     */
    public Quantity(String quantity, boolean internalCall) {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (internalCall) {
            AppUtil.checkArgument(isValidQuantity(trimmedQuantity), INTERNAL_MESSAGE_CONSTRAINTS);
            this.quantity = Integer.parseInt(trimmedQuantity);
        }
    }

    private Quantity(int quantity) throws ParseException {
        if (quantity < 0) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
        this.quantity = quantity;
    }

    public static boolean isValidQuantity(String test) {
        return StringUtil.isNonNegativeInteger(test);
    }
    /**
     * Returns true if a given input string is a valid quantity.
     */
    public static boolean isValidInputQuantity(String test) {
        return StringUtil.isPositiveInteger(test);
    }

    /**
     * Returns true if quantity is zero.
     */
    public static boolean quantityIsZero(Quantity quantity) {
        return quantity.quantity == 0;
    }

    public boolean isLessThan(Quantity deductAmount) {
        return this.quantity < deductAmount.quantity;
    }

    /**
     * Reduces quantity by specified amount.
     *
     * @param deductAmount Amount to be deducted.
     * @return Quantity to be deducted.
     * @throws ParseException if new quantity is negative.
     */
    public Quantity deductQuantity(Quantity deductAmount) throws ParseException {
        Quantity newQuantity;
        newQuantity = new Quantity(this.quantity - deductAmount.quantity);
        return newQuantity;
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
