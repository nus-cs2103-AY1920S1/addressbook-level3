package io.xpire.model.item;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;
import io.xpire.commons.util.StringUtil;

/**
 * Represents the quantity of an xpireItem.
 * Only positive integers are accepted as the input quantity.
 */
public class Quantity {

    public static final String DEFAULT_QUANTITY = "1";
    public static final String MESSAGE_CONSTRAINTS =
            "Quantity provided should be an unsigned positive integer with no leading 0s";
    public static final String MESSAGE_QUANTITY_LIMIT = "Quantity exceeds maximum input quantity of 100000";
    public static final int MAX_VALUE = 100000;
    private int quantity;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid input quantity, i.e. non-negative integer not exceeding maximum allowed limit.
     */
    public Quantity(String quantity) {
        String trimmedQuantity = quantity.trim();
        requireNonNull(trimmedQuantity);
        AppUtil.checkArgument(isValidQuantity(trimmedQuantity), MESSAGE_CONSTRAINTS);
        this.quantity = Integer.parseInt(trimmedQuantity);
    }

    private Quantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns true if a given input string is a valid quantity, i.e. quantity that is positive.
     */
    public static boolean isValidQuantity(String test) {
        return StringUtil.isNonZeroUnsignedInteger(test) && Integer.parseInt(test) <= MAX_VALUE;
    }

    /**
     * Returns true if a given input string is numeric but exceeds given range.
     */
    public static boolean isNumericButExceedQuantity(String test) {
        return StringUtil.isUnsignedNumericWithoutLeadingZeroes(test) && test.length() > 6;
    }

    /**
     * Returns true if quantity is zero.
     */
    public static boolean quantityIsZero(Quantity quantity) {
        return quantity.quantity == 0;
    }

    /**
     * Checks if an item's quantity is less than the input quantity for validity.
     *
     * @param deductAmount Quantity to be deducted from item.
     * @return true if this quantity is less than the input quantity for deduction.
     */
    public boolean isLessThan(Quantity deductAmount) {
        return this.quantity < deductAmount.quantity;
    }

    /**
     * Checks if the sum of this quantity and the input quantity exceeds the maximum limit.
     *
     * @param quantity input quantity to be added to this quantity.
     * @return true if their sum exceeds the maximum allowed quantity.
     */
    public boolean sumExceedsMaximumLimit(Quantity quantity) {
        return this.quantity + quantity.quantity > MAX_VALUE;
    }

    /**
     * Reduces quantity by specified amount.
     * Guarantees: new quantity should be non-negative.
     *
     * @param deductAmount Amount to be deducted.
     * @return Quantity to be deducted.
     */
    public Quantity deductQuantity(Quantity deductAmount) {
        Quantity newQuantity;
        newQuantity = new Quantity(this.quantity - deductAmount.quantity);
        return newQuantity;
    }

    /**
     * Increases quantity by specified amount.
     * Guarantees: new quantity should not exceed maximum limit.
     *
     * @param increaseAmount quantity to be increased.
     * @return new Quantity of item.
     */
    public Quantity increaseQuantity(Quantity increaseAmount) {
        Quantity newQuantity = new Quantity(this.quantity + increaseAmount.quantity);
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
