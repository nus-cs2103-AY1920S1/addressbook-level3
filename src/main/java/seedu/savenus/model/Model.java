package seedu.savenus.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Food> PREDICATE_SHOW_ALL_FOOD = unused -> true;

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
     * Returns the user prefs' menu file path.
     */
    Path getMenuFilePath();

    /**
     * Sets the user prefs' menu file path.
     */
    void setMenuFilePath(Path menuFilePath);

    /**
     * Replaces menu data with the data in {@code menu}.
     */
    void setMenu(ReadOnlyMenu menu);

    /** Returns the $aveNUS menu */
    ReadOnlyMenu getMenu();

    /**
     * Returns true if a food with the same identity as {@code food} exists in the $aveNUS menu.
     */
    boolean hasFood(Food food);

    /**
     * Deletes the given food.
     * The food must exist in the menu.
     */
    void deleteFood(Food target);

    /**
     * Adds the given food.
     * {@code food} must not already exist in the menu.
     */
    void addFood(Food food);

    /**
     * Replaces the given food {@code target} with {@code editedFood}.
     * {@code target} must exist in the menu.
     * The food identity of {@code editedFood} must not be the same as another existing food in the menu.
     */
    void setFood(Food target, Food editedFood);

    /**
     * Getter for current user's {@code budget} amount
     */
    float getRemainingBudget();

    /**
     * Setter for current user's {@code Budget} to new {@code Budget}
     */
    void setRemainingBudget(RemainingBudget newRemainingBudget);

    /**
     * Getter for current user's {@code daysToExpire}
     */
    int getDaysToExpire();

    /**
     * Setter for current user's {@code DaysToExpire} to new {@code DaysToExpire}
     */
    void setDaysToExpire(DaysToExpire newDaysToExpire);


    /** Returns an unmodifiable view of the filtered food list */
    ObservableList<Food> getFilteredFoodList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);
}
