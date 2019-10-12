package calofit.model.dish;

import static calofit.commons.util.StringUtil.isNonZeroUnsignedInteger;

import calofit.commons.util.AppUtil;

/**
 * Represents a Dish's calorie value in the dish database.
 */
public class Calorie {

    public static final String MESSAGE_CONSTRAINTS =
            "Calories should only be positive values that are not 0";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String calories;

    /**
     * Constructs a {@code Calorie}.
     *
     * @param calorieValue a valid calorie value.
     */
    public Calorie(String calorieValue) {
        AppUtil.checkArgument(isNonZeroUnsignedInteger(calorieValue), MESSAGE_CONSTRAINTS);
        calories = calorieValue;
    }

    /**
     * Returns true if a given string is a valid name.
     * @param test a string
     * @return true if calorie value is valid
     */
    public static boolean isValidCalorie(String test) {
        return isNonZeroUnsignedInteger(test);
        //return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return calories;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calorie // instanceof handles nulls
                && calories.equals(((Calorie) other).calories)); // state check
    }

    @Override
    public int hashCode() {
        return calories.hashCode();
    }
}
