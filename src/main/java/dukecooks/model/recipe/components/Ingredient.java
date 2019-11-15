package dukecooks.model.recipe.components;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents a Ingredient in Duke Cooks.
 * Guarantees: immutable; name is valid as declared in {@link #isValidIngredientName(String)}
 */
public class Ingredient {

    public static final String MESSAGE_CONSTRAINTS =
            "Ingredients names should only contain alphanumeric characters and spaces, limited to 20 characters";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]{0,19}";

    public final String ingredientName;

    /**
     * Constructs a {@code Ingredient}.
     *
     * @param ingredientName A valid ingredient name.
     */
    public Ingredient(String ingredientName) {
        requireNonNull(ingredientName);
        AppUtil.checkArgument(isValidIngredientName(ingredientName), MESSAGE_CONSTRAINTS);
        this.ingredientName = ingredientName;
    }

    /**
     * Returns true if a given string is a valid ingredient name.
     */
    public static boolean isValidIngredientName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ingredient // instanceof handles nulls
                && ingredientName.equals(((Ingredient) other).ingredientName)); // state check
    }

    @Override
    public int hashCode() {
        return ingredientName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + ingredientName + ']';
    }

}
