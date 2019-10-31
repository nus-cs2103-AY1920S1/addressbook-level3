package seedu.savenus.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.ReadOnlyPurchaseHistory;
import seedu.savenus.model.recommend.RecommendationSystem;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.ReadOnlyUserPrefs;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
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

    /**
     * Returns the $aveNUS menu
     */
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
     * Gets back the original list.
     */
    ObservableList<Food> getFoods();

    /**
     * Simply sets the auto-sort flag.
     * @param autoSortFlag the auto-sort flag.
     */
    void setAutoSortFlag(boolean autoSortFlag);

    /**
     * Gets the auto-sort flag.
     * @return the auto-sort flag as a boolean value.
     */
    boolean getAutoSortFlag();

    /**
     * A simple method to replace the filtered food list with the contents of a new list.
     *
     * @param fieldList the new list of food.
     */
    void editFilteredFoodList(List<String> fieldList);

    /**
     * Returns the user prefs' purchase history file path.
     */
    Path getPurchaseHistoryFilePath();

    /**
     * Sets the user prefs' purchase history file path.
     */
    void setPurchaseHistoryFilePath(Path menuFilePath);

    /**
     * Returns user's wallet
     */
    Wallet getWallet();

    /**
     * Set user's wallet
     */
    void setWallet(Wallet newWallet);

    /**
     * Returns the user prefs' wallet file path.
     */
    Path getWalletFilePath();

    /**
     * Sets the user prefs' wallet file path.
     */
    void setWalletFilePath(Path walletFilePath);

    /** Returns the $aveNUS purchase history */
    ReadOnlyPurchaseHistory getPurchaseHistory();

    /**
     * Replaces purchasing history data with the data in {@code purchaseHistory}.
     */
    void setPurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory);

    /** Returns an unmodifiable view of the {@code PurchaseHistory} */
    ObservableList<Purchase> getPurchaseHistoryList();

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
     * Deduct money from wallet.
     */
    void deductFromWallet(Price price) throws InsufficientFundsException;

    /**
     * Deduct money from wallet.
     */
    void deductFromWallet(Savings savings) throws InsufficientFundsException;

    /**
     * Buy Food.
     *
     * @param foodToBuy Food to buy
     */
    void buyFood(Food foodToBuy) throws InsufficientFundsException;

    /**
     * Returns an unmodifiable view of the filtered food list
     */
    ObservableList<Food> getFilteredFoodList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);

    /**
     * Gets the current recommendation system.
     */
    RecommendationSystem getRecommendationSystem();

    /**
     * Updates if the recommendation system is currently in use.
     *
     * @throws NullPointerException if {@code inUse} is null.
     */
    void setRecommendationSystemInUse(boolean inUse);

    /**
     * Get a history of the list of commands
     */
    List<String> getCommandHistory();

    /**
     * Updates the user's liked categories, tags and locations.
     *
     * @throws NullPointerException if {@code categoryList}, {@code tagList} or {@code locationList} is null.
     */
    void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Updates the user's disliked categories, tags and locations.
     *
     * @throws NullPointerException if {@code categoryList}, {@code tagList} or {@code locationList} is null.
     */
    void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /** Removes the user's liked categories, tags and locations.
     * @throws NullPointerException if {@code categoryList}, {@code tagList} or {@code locationList} is null.
     */
    void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Removes the user's disliked categories, tags and locations.
     * @throws NullPointerException if {@code categoryList}, {@code tagList} or {@code locationList} is null.
     */
    void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Clears the user's liked categories, tags and locations.
     */
    void clearLikes();

    /**
     * Clears the user's liked categories, tags and locations.
     */
    void clearDislikes();

    /**
     * Sets the custom comparator.
     */
    void setCustomSorter(List<String> fields);

    /**
     * Gets the custom comparator.
     */
    CustomSorter getCustomSorter();

    /**
     * Add money from wallet to savings account.
     */
    void addToSavings(Savings savings);

    /**
     * Return an unmodifiable version of the user's SavingsAccount.
     */
    ReadOnlySavingsAccount getSavingsAccount();

    /**
     * Replaces purchasing history data with the data in {@code purchaseHistory}.
     */
    void setSavingsAccount(ReadOnlySavingsAccount savingsAccount);

    /**
     * Gets the list of aliases from the model.
     */
    AliasList getAliasList();

    /**
     * Changes the alias list.
     */
    void setAliasList(AliasList aliasList);
}
