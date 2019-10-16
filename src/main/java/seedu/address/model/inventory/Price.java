package seedu.address.model.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * represents the price of inventory
 * ensures the price is a positive value
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price should be a positive value";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final double price;

    /**
     * Constructs a {@code Name}.
     *
     * @param price A valid name.
     */
    public Price(double price) {
        requireNonNull(price);
        checkArgument(isValidMemberName(price), MESSAGE_CONSTRAINTS);
        this.price = price;
    }

    public Price() {
        price = -1;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMemberName(double price) {
        return price >= 0;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return Double.toString(price);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && (price == (((Price) other).price))); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(price).hashCode();
    }
}
