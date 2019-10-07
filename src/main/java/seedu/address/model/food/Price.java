package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's price number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {


    public static final String MESSAGE_CONSTRAINTS =
            "Price numbers should only contain numbers, and only in 2 decimal places";
    public static final String VALIDATION_REGEX = "\\d+(\\.\\d{2,2})?";
    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price number.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = convert(price);
    }

    /**
     * Returns true if a given string is a valid price number.
     */
    public static boolean isValidPrice(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            try {
                Double.parseDouble(test);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the input price has decimal digits or not.
     * @param price the input String.
     * @return true if the input price has no decimal digits.
     */
    public boolean isPerfectNumber(String price) {
        try {
            Integer.parseInt(price);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Converts the input price to a more accurate representation.
     * @param price the input String.
     * @return the correct String representation of the input price.
     */
    public String convert(String price) {
        if (isPerfectNumber(price)) {
            return Integer.parseInt(price) + ".00";
        } else {
            return String.format("%.2f", Double.parseDouble(price));
        }
    }

    @Override
    public String toString() {
        return value;
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
