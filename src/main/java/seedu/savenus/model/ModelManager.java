package seedu.savenus.model;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Menu menu;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;

    private Optional<Comparator<Food>> recommendationComparator;

    /**
     * Initializes a ModelManager with the given menu and userPrefs.
     */
    public ModelManager(ReadOnlyMenu menu, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(menu, userPrefs);

        logger.fine("Initializing with address book: " + menu + " and user prefs " + userPrefs);

        this.menu = new Menu(menu);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoods = new FilteredList<>(this.menu.getFoodList());

        // Initialize recommendationComparator to default
        recommendationComparator = Optional.empty();
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
    public void buyFood(Food target) {
        menu.buyFood(target);
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

    //=========== Wallet Accessors =========================================================================

    /**
     * Get user's {@code Wallet}.
     */
    public Wallet getWallet() {
        return menu.getWallet();
    }

    @Override
    public double getRemainingBudget() {
        return menu.getWallet().getRemainingBudgetAmount();
    }

    @Override
    public void setRemainingBudget(RemainingBudget newRemainingBudget) {
        requireAllNonNull(newRemainingBudget);
        menu.getWallet().setRemainingBudget(newRemainingBudget);
    }

    @Override
    public int getDaysToExpire() {
        return menu.getWallet().getNumberOfDaysToExpire();
    }

    @Override
    public void setDaysToExpire(DaysToExpire newDaysToExpire) {
        requireAllNonNull(newDaysToExpire);
        menu.getWallet().setDaysToExpire(newDaysToExpire);
    }

    //=========== Filtered Food List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Food} backed by the internal list of
     * {@code versionedMenu}
     */
    @Override
    public ObservableList<Food> getFilteredFoodList() {
        if (recommendationComparator.isPresent()) {
            return filteredFoods.sorted(recommendationComparator.get());
        } else {
            return filteredFoods;
        }
    }


    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoods.setPredicate(predicate);
    }

    // Updates the comparator used for recommendations
    @Override
    public void updateRecommendationComparator(Comparator<Food> recommendationComparator) {
        requireNonNull(recommendationComparator);
        this.recommendationComparator = Optional.of(recommendationComparator);
    }

    @Override
    public void resetRecommendationComparator() {
        this.recommendationComparator = Optional.empty();
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
}
