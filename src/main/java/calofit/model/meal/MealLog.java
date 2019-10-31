package calofit.model.meal;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.DoubleExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import calofit.commons.util.CollectionUtil;
import calofit.commons.util.ObservableListUtil;
import calofit.model.dish.exceptions.DishNotFoundException;
import calofit.model.meal.exceptions.DuplicateMealException;

/**
 * Represents all meals tracked by the application.
 * Contains the original list of all meals input by the user.
 * Stores other lists of meals that are generated based on the original list of meals.
 */
public class MealLog implements ReadOnlyMealLog {
    private List<Meal> mealLog = new ArrayList<>();
    private ObservableList<Meal> observableMeals = FXCollections.observableList(mealLog);
    private ObservableList<Meal> readOnlyMeals = FXCollections.unmodifiableObservableList(observableMeals);
    private ObservableList<Meal> todayMeals = observableMeals.filtered(MealLog::isMealToday);
    private ObservableList<Meal> currentMonthMeals = observableMeals.filtered(MealLog::isMealThisMonth);

    private final DoubleExpression todayCalories = ObservableListUtil.sum(ObservableListUtil.lazyMap(todayMeals,
        meal -> (double) meal.getDish().getCalories().getValue()));

    public MealLog() {}

    public MealLog (MealLog toBeCopied) {
        for (int i = 0; i < toBeCopied.observableMeals.size(); i++) {
            this.observableMeals.add(toBeCopied.observableMeals.get(i));
        }
    }

    public MealLog(ReadOnlyMealLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code MealLog} with {@code newData}.
     */
    public void resetData(ReadOnlyMealLog newData) {
        requireNonNull(newData);

        setMeals(newData.getMealLog());
    }

    /**
     * Replaces the contents of this list with {@code meals}.
     * {@code meals} must not contain duplicate meals.
     */
    public void setMeals(List<Meal> meals) {
        CollectionUtil.requireAllNonNull(meals);
        if (!mealsAreUnique(meals)) {
            throw new DuplicateMealException();
        }

        observableMeals.setAll(meals);
    }

    /**
     * Returns true if {@code meals} contains only unique meals.
     */
    private boolean mealsAreUnique(List<Meal> meals) {
        for (int i = 0; i < meals.size() - 1; i++) {
            for (int j = i + 1; j < meals.size(); j++) {
                if (meals.get(i).isSameMeal(meals.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the Meal object is created today.
     * @param meal the Meal to be tested
     * @return the boolean representing whether the meal is created today.
     */
    private static boolean isMealToday(Meal meal) {
        return meal.getTimestamp()
            .getDateTime().toLocalDate()
            .equals(LocalDate.now());
    }

    /**
     * Checks if the Meal object is created this month.
     * @param meal is the Meal to be tested
     * @return the boolean representing whether the Meal is created in this month.
     */
    private static boolean isMealThisMonth(Meal meal) {
        return meal.getTimestamp()
                .getDateTime().toLocalDate().getMonth()
                .equals(LocalDate.now().getMonth());
    }

    /**
     * Get a list of meals eaten by the user.
     * @return Meal list
     */
    @Override
    public ObservableList<Meal> getMealLog() {
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
     */
    public void removeMeal(Meal meal) {
        requireNonNull(meal);
        if (!observableMeals.remove(meal)) {
            throw new DishNotFoundException();
        }
    }

    public void setMeal(Meal target, Meal editedMeal) {
        CollectionUtil.requireAllNonNull(target, editedMeal);

        int index = observableMeals.indexOf(target);
        if (index == -1) {
            throw new DishNotFoundException();
        }

        observableMeals.set(index, editedMeal);
    }

    /**
     * Checks if a meal is in the meal log.
     * @param meal Meal to check
     * @return True if meal was in the Log, false otherwise.
     */
    public boolean hasMeal(Meal meal) {
        return observableMeals.contains(meal);
    }

    /**
     * Gets the list of meals eaten by the user today.
     * @return the filtered Meal List that checks the TimeStamp of each Meal.
     */
    public ObservableList<Meal> getTodayMeals() {
        return todayMeals;
    }

    /**
     * Gets the list of meals eaten by the user this month.
     * @return the filtered Meal List that checks the TimeStamp of each Meal.
     */
    public ObservableList<Meal> getCurrentMonthMeals() {
        return currentMonthMeals;
    }

    public DoubleExpression getTodayCalories() {
        return todayCalories;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MealLog // instanceof handles nulls
                && mealLog.equals(((MealLog) other).mealLog));
    }
}
