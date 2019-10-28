package dukecooks.model.mealplan.components;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.model.recipe.components.Ingredient;

/**
 * Represents a MealPlan in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MealPlan {

    // Identity fields
    private final MealPlanName name;

    // Data fields
    private final Set<Ingredient> ingredients = new HashSet<>();
    /**
     * Every field must be present and not null.
     */
    public MealPlan(MealPlanName name) {
        CollectionUtil.requireAllNonNull(name);
        this.name = name;
    }

    public MealPlanName getName() {
        return name;
    }

    /**
     * Returns true if both meal plans of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two meal plans.
     */
    public boolean isSameMealPlan(MealPlan otherMealPlan) {
        if (otherMealPlan == this) {
            return true;
        }

        return otherMealPlan != null
                && otherMealPlan.getName().equals(getName());
    }

    /**
     * Returns true if both meal plans have the same identity and data fields.
     * This defines a stronger notion of equality between two meal plans.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MealPlan)) {
            return false;
        }

        MealPlan otherMealPlan = (MealPlan) other;
        return otherMealPlan.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ingredients);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
