package dukecooks.model.mealplan;

import dukecooks.model.mealplan.components.MealPlan;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Duke Cooks
 */
public interface ReadOnlyMealPlanBook {

    /**
     * Returns an unmodifiable view of the meal plans list.
     * This list will not contain any duplicate meal plans.
     */
    ObservableList<MealPlan> getMealPlanList();

}
