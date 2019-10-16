package calofit.model.meal;

import java.time.LocalDate;
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
    private ObservableList<Meal> todayMeals = observableMeals.filtered(MealLog::isMealToday);
    private ObservableList<Meal> currentMonthMeals = observableMeals.filtered(MealLog::isMealThisMonth);

    private static boolean isMealToday(Meal meal) {
        return meal.getTimestamp()
            .getDateTime().toLocalDate()
            .equals(LocalDate.now());
    }

    private static boolean isMealThisMonth(Meal meal) {
        return meal.getTimestamp()
                .getDateTime().toLocalDate().getMonth()
                .equals(LocalDate.now().getMonth());
    }
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

    public ObservableList<Meal> getTodayMeals() {
        return todayMeals;
    }

    public ObservableList<Meal> getCurrentMonthMeals() {
        return currentMonthMeals;
    }
}
