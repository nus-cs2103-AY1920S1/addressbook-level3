package dukecooks.testutil.mealplan;

import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * A utility class to help with building MealPlanBook objects.
 * Example usage: <br>
 *     {@code MealPlanBook dc = new MealPlanBookBuilder().withMealPlan("Fried", "Chicken").build();}
 */
public class MealPlanBookBuilder {

    private MealPlanBook mealPlanBook;

    public MealPlanBookBuilder() {
        mealPlanBook = new MealPlanBook();
    }

    public MealPlanBookBuilder(MealPlanBook mealPlanBook) {
        this.mealPlanBook = mealPlanBook;
    }

    /**
     * Adds a new {@code MealPlan} to the {@code MealPlanBook} that we are building.
     */
    public MealPlanBookBuilder withMealPlan(MealPlan mealPlan) {
        mealPlanBook.addMealPlan(mealPlan);
        return this;
    }

    public MealPlanBook build() {
        return mealPlanBook;
    }
}
