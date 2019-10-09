package calofit.model.meal;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents all meals tracked by the application.
 */
public class MealLog {
    private List<Meal> mealLog = new ArrayList<>();
    private ObservableList<Meal> observableMeals = FXCollections.observableList(mealLog);
    private ObservableList<Meal> readOnlyMeals = FXCollections.unmodifiableObservableList(observableMeals);

    /**
     * Get a list of meals eaten by the user.
     * @return Meal list
     */
    public ObservableList<Meal> getMeals() {
        return readOnlyMeals;
    }

    /**
     * Add a meal to the meal log.
     * @param meal Meal to add
     * @return True if meal was added, false otherwise.
     */
    public boolean addMeal(Meal meal) {
        return observableMeals.add(meal);
    }

    /**
     * Remove a meal from the meal log.
     * @param meal Meal to remove
     * @return True if meal was in log and got removed, false otherwise.
     */
    public boolean removeMeal(Meal meal) {
        return observableMeals.remove(meal);
    }
}
