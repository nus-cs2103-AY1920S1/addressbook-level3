package dukecooks.model.recipe.components;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dukecooks.commons.util.CollectionUtil;

/**
 * Represents a Recipe in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {

    // Identity fields
    private final RecipeName name;

    // Data fields
    private final Set<Ingredient> ingredients = new HashSet<>();
    private final Calories calories;
    private final Carbs carbs;
    private final Fats fats;
    private final Protein protein;

    /**
     * Every field must be present and not null.
     */
    public Recipe(RecipeName name, Set<Ingredient> ingredients,
                  Calories calories, Carbs carbs, Fats fats, Protein protein) {
        CollectionUtil.requireAllNonNull(name, ingredients);
        this.name = name;
        this.ingredients.addAll(ingredients);
        this.calories = calories;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
    }

    public RecipeName getName() {
        return name;
    }

    public Calories getCalories() {
        return calories;
    }

    public Carbs getCarbs() {
        return carbs;
    }

    public Fats getFats() {
        return fats;
    }

    public Protein getProtein() {
        return protein;
    }

    /**
     * Returns an immutable ingredient set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Ingredient> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    /**
     * Returns true if both recipes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSameRecipe(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null
                && otherRecipe.getName().equals(getName());
    }

    /**
     * Returns true if both recipes have the same identity and data fields.
     * This defines a stronger notion of equality between two recipes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recipe)) {
            return false;
        }

        Recipe otherRecipe = (Recipe) other;
        return otherRecipe.getName().equals(getName())
                && otherRecipe.getIngredients().equals(getIngredients());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ingredients);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Ingredients: ");
        getIngredients().forEach(builder::append);
        return builder.toString();
    }

}
