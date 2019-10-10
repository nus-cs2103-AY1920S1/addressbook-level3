package seedu.sgm.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a kind of nutrition value of <code>Food</code> in the recommended food list. Guarantees: immutable; is
 * valid as declared in {@link #isValidValue(String)}
 */
public class NutritionValue {

    public static final String MESSAGE_CONSTRAINTS =
            "Nutrition value should only contain number and should be non-negative.";
    public static final String VALIDATION_REGEX = "^[+]?\\d+\\.?\\d*";

    public final String value;

    /**
     * Constructs a {@code NutritionValue}.
     *
     * @param value a valid nutrition value
     */
    public NutritionValue(String value) {
        requireNonNull(value);
        checkArgument(isValidValue(value.toString()), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid nutrition value.
     */
    public static boolean isValidValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the double value indicated in the {@code value} string
     */
    public double getNumericalValue() {
        return Double.parseDouble(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NutritionValue)) {
            return false;
        }
        return value.equals(((NutritionValue) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

