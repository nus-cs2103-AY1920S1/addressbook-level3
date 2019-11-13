package calofit.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import calofit.commons.core.GuiSettings;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Statistics;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Dish> PREDICATE_SHOW_DEFAULT = null;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' dish database file path.
     */
    Path getDishDatabaseFilePath();

    /**
     * Sets the user prefs' dish database file path.
     */
    void setDishDatabaseFilePath(Path dishDatabaseFilePath);

    /**
     * Replaces dish database data with the data in {@code dishDatabase}.
     */
    void setDishDatabase(ReadOnlyDishDatabase dishDatabase);

    /** Returns the DishDatabase */
    ReadOnlyDishDatabase getDishDatabase();

    /**
     * Returns true if a dish with the same identity as {@code dish} exists in the dish database.
     */
    boolean hasDish(Dish dish);

    Dish getDish(Dish dish);

    /**
     * Deletes the given dish.
     * The dish must exist in the dish database.
     */
    void deleteDish(Dish target);

    /**
     * Adds the given dish.
     * {@code dish} must not already exist in the dish database.
     */
    void addDish(Dish dish);

    /**
     * Replaces the given dish {@code target} with {@code editedDish}.
     * {@code target} must exist in the dish database.
     * The dish identity of {@code editedDish} must not be the same as another existing dish in the dish database.
     */
    void setDish(Dish target, Dish editedDish);

    /** Returns an unmodifiable view of the filtered dish list */
    ObservableList<Dish> getFilteredDishList();

    /**
     * Updates the filter of the filtered dish list to filter by the given {@code predicate}.
     * If null, resets to the default predicate.
     */
    void setDishFilterPredicate(Predicate<Dish> predicate);

    /**
     * Returns an unmodifiable view of the filtered Meal List.
     */
    ObservableList<Meal> getTodayMealList();

    void addMeal(Meal meal);

    void removeMeal(Meal meal);

    void setMeal(Meal target, Meal editedMeal);

    /**
     * Returns the {@code MealLog} that wraps around the list of meals input by the user.
     */
    MealLog getMealLog();

    /**
     * Returns the {@code Statistics} object that wraps around the data in CaloFit to be displayed to the user.
     */
    Statistics getStatistics();

    /**
     * Returns the Dish by checking with the Dish Database.
     * @param dishName is the name of the dish to be searched.
     * @return the Dish stored in the database if found or null if not found.
     */
    Dish getDishByName(Name dishName);

    /**
     * Checks if the Dish is in the Dish Database.
     * @param dish is the Dish to be checked.
     * @return a boolean representing whether the Dish is found in the database.
     */
    boolean hasDishName(Name dish);

    /**
     * Gets the CalorieBudget that wraps around the calorie budget history of CaloFit.
     * @return the CalorieBudget wrapper object.
     */
    CalorieBudget getCalorieBudget();

    /**
     * Gets the remaining calories that the user has left for the day.
     * @return the remaining calories.
     */
    int getRemainingCalories();

    ObjectProperty<LocalDateTime> nowProperty();
}
