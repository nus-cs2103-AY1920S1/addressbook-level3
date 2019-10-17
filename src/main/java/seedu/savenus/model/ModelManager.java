package seedu.savenus.model;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.FoodFilter;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

/**
 * Represents the in-memory model of the menu data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final Menu menu;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;
    private final ObservableList<Purchase> purchaseHistory;
    private final RecommendationSystem recommendationSystem;

    /**
     * Initializes a ModelManager with the given menu and userPrefs.
     */
    public ModelManager(ReadOnlyMenu menu, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(menu, userPrefs);

        logger.fine("Initializing with $aveNUS menu: " + menu + " and user prefs " + userPrefs);

        this.menu = new Menu(menu);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoods = new FilteredList<>(this.menu.getFoodList());
        purchaseHistory = this.menu.getPurchaseHistory();

        this.recommendationSystem = new RecommendationSystem();
    }

    public ModelManager() {
        this(new Menu(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getMenuFilePath() {
        return userPrefs.getMenuFilePath();
    }

    @Override
    public void setMenuFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        userPrefs.setMenuFilePath(menuFilePath);
    }

    //=========== Menu ================================================================================

    @Override
    public void setMenu(ReadOnlyMenu menu) {
        this.menu.resetData(menu);
    }

    @Override
    public ReadOnlyMenu getMenu() {
        return menu;
    }

    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return menu.hasFood(food);
    }

    @Override
    public void deleteFood(Food target) {
        menu.removeFood(target);
    }

    @Override
    public void addFood(Food food) {
        menu.addFood(food);
        updateFilteredFoodList(PREDICATE_SHOW_ALL_FOOD);
    }

    @Override
    public void setFood(Food target, Food editedFood) {
        requireAllNonNull(target, editedFood);
        menu.setFood(target, editedFood);
    }

    @Override
    public void setFoods(List<Food> list) {
        requireNonNull(list);
        menu.setFoods(list);
    }

    //=========== PurchaseHistory Methods =========================================================================

    @Override
    public void addPurchase(Purchase target) {
        menu.addPurchase(target);
    }

    @Override
    public void removePurchase(Purchase target) {
        menu.removePurchase(target);
    }

    //=========== Filtered Purchase List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the {@code PurchaseHistory} backed by the internal list of
     * {@code versionedMenu}
     */
    @Override
    public ObservableList<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }

    //=========== Wallet Accessors =========================================================================

    /**
     * Get user's {@code Wallet}.
     */
    public Wallet getWallet() {
        return menu.getWallet();
    }

    @Override
    public BigDecimal getRemainingBudget() {
        return menu.getWallet().getRemainingBudgetAmount();
    }

    @Override
    public void setRemainingBudget(RemainingBudget newRemainingBudget) throws CommandException {
        requireNonNull(newRemainingBudget);
        if (newRemainingBudget.getRemainingBudget().compareTo(new BigDecimal(1000000.00)) == 1) {
            throw new CommandException(RemainingBudget.FLOATING_POINT_CONSTRAINTS);
        }
        menu.getWallet().setRemainingBudget(newRemainingBudget);
    }

    @Override
    public int getDaysToExpire() {
        return menu.getWallet().getNumberOfDaysToExpire();
    }

    @Override
    public void setDaysToExpire(DaysToExpire newDaysToExpire) throws CommandException {
        requireNonNull(newDaysToExpire);
        if (newDaysToExpire.getDaysToExpire() > 365) {
            throw new CommandException(DaysToExpire.INTEGER_CONSTRAINTS);
        }
        menu.getWallet().setDaysToExpire(newDaysToExpire);
    }

    @Override
    public void buyFood(Food foodToBuy) throws CommandException {
        requireNonNull(foodToBuy);
        menu.getWallet().pay(foodToBuy.getPrice());
    }

    //=========== Filtered Food List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Food} backed by the internal list of
     * {@code versionedMenu}
     */
    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return filteredFoods
                .filtered(recommendationSystem.getRecommendationPredicate())
                .sorted(recommendationSystem.getRecommendationComparator());
    }


    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoods.setPredicate(predicate);
    }

    @Override
    public void editFilteredFoodList(List<String> fieldList) {
        requireNonNull(fieldList);
        filteredFoods.setPredicate(new FoodFilter(fieldList));
    }

    //=========== Recommendation System =============================================================
    @Override
    public RecommendationSystem getRecommendationSystem() {
        return recommendationSystem;
    }

    @Override
    public void updateRecommendationComparator(Comparator<Food> recommendationComparator) {
        requireNonNull(recommendationComparator);
        this.recommendationSystem.setRecommendationComparator(recommendationComparator);
    }

    @Override
    public void updateRecommendationPredicate(Predicate<Food> recommendationPredicate) {
        requireNonNull(recommendationPredicate);
        this.recommendationSystem.setRecommendationPredicate(recommendationPredicate);
    }

    @Override
    public void setRecommendationSystemInUse(boolean inUse) {
        this.recommendationSystem.setInUse(inUse);
    }

    @Override
    public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        recommendationSystem.addLikes(categoryList, tagList, locationList);
    }

    @Override
    public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        recommendationSystem.addDislikes(categoryList, tagList, locationList);
    }

    @Override
    public void clearLikes() {
        recommendationSystem.clearLikes();
    }

    @Override
    public void clearDislikes() {
        recommendationSystem.clearDislikes();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return menu.equals(other.menu)
                && userPrefs.equals(other.userPrefs)
                && filteredFoods.equals(other.filteredFoods);
    }

    @Override
    public List<String> getCommandHistory() {
        return CommandHistory.getCommandHistory();
    }
}
