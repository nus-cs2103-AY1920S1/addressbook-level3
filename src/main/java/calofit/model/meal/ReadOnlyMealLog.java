package calofit.model.meal;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an dish database
 */
public interface ReadOnlyMealLog {

    /**
     * Returns an unmodifiable view of the MealLog.
     * This list will not contain any duplicate meals.
     */
    ObservableList<Meal> getMealLog();
}
