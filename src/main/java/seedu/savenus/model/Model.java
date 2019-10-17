package seedu.savenus.model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.tag.Tag;
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
     * Replace the original list with a new list.
     */
    void setFoods(List<Food> list);

    /**
     * A simple method to replace the filtered food list with the contents of a new list.
     * @param fieldList the new list of food.
     */
    void editFilteredFoodList(List<String> fieldList);

    /**
     * Buy the given food.
     * The food must exist in the menu.
     */
    void addPurchase(Purchase target);

    /**
     * Remove purchase.
     * The purchase must exist in the purchase history.
     */
    void removePurchase(Purchase target);

    /**
     * Getter for current user's {@code budget} amount
     */
    BigDecimal getRemainingBudget();

    /**
     * Setter for current user's {@code Budget} to new {@code Budget}
     */
    void setRemainingBudget(RemainingBudget newRemainingBudget) throws CommandException;

    /**
     * Getter for current user's {@code daysToExpire}
     */
    int getDaysToExpire();

    /**
     * Setter for current user's {@code DaysToExpire} to new {@code DaysToExpire}
     */
    void setDaysToExpire(DaysToExpire newDaysToExpire) throws CommandException;

    /**
     * Buy Food.
     * @param foodToBuy Food to buy
     */
    void buyFood(Food foodToBuy) throws CommandException;

    /** Returns an unmodifiable view of the filtered food list */
    ObservableList<Food> getFilteredFoodList();

    /** Returns an unmodifiable view of the {@code PurchaseHistory} */
    ObservableList<Purchase> getPurchaseHistory();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);

    /**
     * Gets the current recommendation system.
     */
    RecommendationSystem getRecommendationSystem();

    /**
     * Updates the comparator of the food list to filter by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateRecommendationComparator(Comparator<Food> recommendationComparator);

    /**
     * Updates the filter of the recommendation system's food list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateRecommendationPredicate(Predicate<Food> recommendationPredicate);

    /**
     * Updates if the recommendation system is currently in use.
     * @throws NullPointerException if {@code inUse} is null.
     */
    void setRecommendationSystemInUse(boolean inUse);

    /**
     * Get a history of the list of commands
     */
    List<String> getCommandHistory();

    /** Updates the user's liked categories, tags and locations.
     * @throws NullPointerException if {@code categoryList}, {@code tagList} or {@code locationList} is null.
     */
    void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Updates the user's disliked categories, tags and locations.
     * @throws NullPointerException if {@code categoryList}, {@code tagList} or {@code locationList} is null.
     */
    void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Clears the user's liked categories, tags and locations.
     */
    void clearLikes();

    /**
     * Clears the user's liked categories, tags and locations.
     */
    void clearDislikes();
}
