package dukecooks.model.recipe.components;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents a recipe's number of calories.
 */
public class Calories {

    public static final String MESSAGE_CONSTRAINTS =
            "Number of calories should only contain numbers, express in kcal, with units omitted.";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;
    public final String cardValue;

    /**
     * Constructs a {@Calorie}.
     *
     * @param calories A valid amount of calories.
     */
    public Calories(String calories) {
        requireNonNull(calories);
        AppUtil.checkArgument(isValidCalories(calories), MESSAGE_CONSTRAINTS);
        value = calories;
        cardValue = "Calories: " + value + "kcal";
    }

    /**
     * Returns true if given string is a valid number of calories
     */
    public static boolean isValidCalories(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
