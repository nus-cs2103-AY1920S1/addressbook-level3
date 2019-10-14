package seedu.savenus.model.food;

import javafx.beans.property.DoubleProperty;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's price number in the menu.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price implements Field {

    public static final String MESSAGE_CONSTRAINTS =
            "Price numbers should only contain numbers, have either 0 or 2 decimal places\n"
            + "Maximum food price allowed is $5000.00 \n"
            + "For example: p/1.50 or p/200";
    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";

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
            } catch (NumberFormatException e) {
                return false;
            }
            if (Double.parseDouble(test) > 5000.00) {
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
     * Converts the input price to a valid string representation.
     * @param price the input String.
     * @return the correct String representation of the input price.
     */
    public String convert(String price) {
        if (isPerfectNumber(price)) {
            return String.format("%d.00", Integer.parseInt(price));
        } else {
            return String.format("%.2f", Double.parseDouble(price));
        }
    }

    /**
     * Returns a price as a double.
     */
    public double toDouble() {
        return Double.parseDouble(value);
    }

    /**
     * Gets the field as a String.
     * @return a String representation of the field.
     */
    public String getField() {
        return this.toString();
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

    @Override
    public int compareTo(Field other) {
        Price otherPrice = (Price) other;
        if (otherPrice == null) {
            return 1;
        } else {
            int thisAmount = (int) (100 * Double.parseDouble(this.getField()));
            int otherAmount = (int) (100 * Double.parseDouble(otherPrice.getField()));
            return thisAmount - otherAmount;
        }
    }
}
