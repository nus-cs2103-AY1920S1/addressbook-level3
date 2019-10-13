package calofit.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import calofit.commons.core.GuiSettings;
import calofit.model.dish.Dish;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Statistics;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Dish> PREDICATE_SHOW_ALL_DISHES = unused -> true;

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
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDishList(Predicate<Dish> predicate);

    ObservableList<Meal> getFilteredMealList();

    void addMeal(Meal meal);

    MealLog getMealLog();

    void updateStatistics();

    Statistics getStatistics();

    Dish getDishByName(Dish dish);

    boolean hasDishName(Dish dish);

    CalorieBudget getCalorieBudget();

    ObservableList<Dish> suggestMeal();
}
