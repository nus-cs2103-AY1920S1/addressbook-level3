package calofit.model.dish;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import calofit.commons.util.AppUtil;
//import calofit.commons.util.StringUtil;

/**
 * Represents a Dish's calorie value in the dish database.
 */
public class Calorie {

    public static final String MESSAGE_CONSTRAINTS =
            "Calories should only be positive integers between 0 and 2 billion";

    public static final Calorie UNKNOWN_CALORIE = new Calorie(-1, true);

    public final int calories;

    /**
     * Constructs a {@code Calorie}
     * @param calorieValue a valid calorie value.
     */
    public Calorie(int calorieValue) {
        AppUtil.checkArgument(calorieValue >= 0, MESSAGE_CONSTRAINTS);
        calories = calorieValue;
    }

    /*
     * Private unsafe constructor that does not validate.
     * Used to construct "fake" calorie values.
     */
    private Calorie(int calorieValue, boolean secret) {
        calories = calorieValue;
    }

    /**
     * Returns true if a given string is a valid name.
     * @param test a string
     * @return true if calorie value is valid
     */
    public static Optional<Calorie> tryParseCalorie(String test) {
        requireNonNull(test);

        try {
            int value = Integer.parseInt(test);
            if (value >= 0 && value <= 2000000000
                && !test.startsWith("+")) { // "+1" is successfully parsed by Integer#parseInt(String)
                return Optional.of(new Calorie(value));
            }
        } catch (NumberFormatException nfe) {
            //Fallthrough to return
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return Integer.toString(calories);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calorie // instanceof handles nulls
                && calories == ((Calorie) other).calories); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(calories);
    }

    public int getValue() {
        return this.calories;
    }
}
