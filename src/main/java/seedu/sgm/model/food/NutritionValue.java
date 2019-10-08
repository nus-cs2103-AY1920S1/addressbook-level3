package seedu.sgm.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a kind of nutrition value of <code>Food</code> in the recommended food list.
 * Guarantees: immutable; is valid as declared in {@link #isValidValue(String)}
 */
public class NutrientionValue {

    public static final String MESSAGE_CONSTRAINTS =
            "Calorie value should only contain numbers. It should be positive and contain no leading zeros.";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*";

    public final Double NutrientionValue;
    /**
     * Constructs a {@code Sugar}.
     *
     * @param NutrientionValue a valid sugar value
     */
    public NutrientionValue(Double NutrientionValue) {
        requireNonNull(NutrientionValue);
        checkArgument(isValidValue(NutrientionValue.toString()), MESSAGE_CONSTRAINTS);
        this.NutrientionValue = NutrientionValue;
    }

    /**
     * Returns true if a given string is a valid sugar value.
     */
    public static boolean isValidValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return NutrientionValue.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Sugar)) {
            return false;
        }
        return NutrientionValue.equals(((Sugar) other).sugarValue);
    }

    @Override
    public int hashCode() {
        return NutrientionValue.hashCode();
    }

}

