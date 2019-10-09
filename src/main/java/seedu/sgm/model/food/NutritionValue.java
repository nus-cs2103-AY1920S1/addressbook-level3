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

    public final Double nutritionValue;

    /**
     * Constructs a {@code NutritionValue}.
     *
     * @param nutritionValue a valid nutrition value
     */
    public NutritionValue(Double nutritionValue) {
        requireNonNull(nutritionValue);
        checkArgument(isValidValue(nutritionValue.toString()), MESSAGE_CONSTRAINTS);
        this.nutritionValue = nutritionValue;
    }

    /**
     * Returns true if a given string is a valid nutrition value.
     */
    public static boolean isValidValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return nutritionValue.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NutritionValue)) {
            return false;
        }
        return nutritionValue.equals(((NutritionValue) other).nutritionValue);
    }

    @Override
    public int hashCode() {
        return nutritionValue.hashCode();
    }

}

