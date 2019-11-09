package seedu.exercise.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.ValidationRegex.ONLY_NON_NEGATIVE_NUMBERS;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

/**
 * Represents a the quantity of an exercise done in the exercise book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {
    public static final String PROPERTY_QUANTITY = "Quantity";
    public static final String MESSAGE_CONSTRAINTS = "Quantity should only contain numbers, and it should not be blank";
    private final String value;

    /**
     * Constructs an {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(ONLY_NON_NEGATIVE_NUMBERS);
    }

    @Override
    public String toString() {
        if (!value.contains(".")) {
            return Integer.toString(Integer.parseInt(value));
        } else {
            double dValue = Double.parseDouble(value);
            if (dValue == (int) dValue) {
                return Integer.toString((int) dValue);
            }
            return Double.toString(dValue);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Quantity // instanceof handles nulls
            && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
