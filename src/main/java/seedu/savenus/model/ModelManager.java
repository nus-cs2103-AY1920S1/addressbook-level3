package seedu.savenus.model;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;

/**
 * Represents the in-memory model of the menu data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Menu menu;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;
    private final RecommendationSystem recommendationSystem;

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

    //=========== Budget Accessors =========================================================================

    @Override
    public double getRemainingBudget() {
        return menu.getWallet().getRemainingBudgetAmount();
    }

    @Override
    public void setRemainingBudget(RemainingBudget newRemainingBudget) {
        requireAllNonNull(newRemainingBudget);
        menu.setRemainingBudget(newRemainingBudget);
    }

    @Override
    public int getDaysToExpire() {
        return menu.getWallet().getNumberOfDaysToExpire();
    }

    @Override
    public void setDaysToExpire(DaysToExpire newDaysToExpire) {
        requireAllNonNull(newDaysToExpire);
        menu.setDaysToExpire(newDaysToExpire);
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
