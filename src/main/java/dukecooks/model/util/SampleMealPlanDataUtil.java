package dukecooks.model.util;

import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;

/**
 * Contains utility methods for populating {@code MealPlanBook} with sample data.
 */
public class SampleMealPlanDataUtil {
    public static MealPlan[] getSampleMealPlans() {
        return new MealPlan[] {
            new MealPlan(new MealPlanName("Tea")),
            new MealPlan(new MealPlanName("Milo")),
            new MealPlan(new MealPlanName("Cheese Omelette")),
            new MealPlan(new MealPlanName("Tuna Sandwich")),
            new MealPlan(new MealPlanName("Chicken Maggi")),
            new MealPlan(new MealPlanName("Fried Chicken"))
        };
    }

    public static ReadOnlyMealPlanBook getSampleMealPlanBook() {
        MealPlanBook sampleDc = new MealPlanBook();
        for (MealPlan sampleMealPlan : getSampleMealPlans()) {
            sampleDc.addMealPlan(sampleMealPlan);
        }
        return sampleDc;
    }

}
