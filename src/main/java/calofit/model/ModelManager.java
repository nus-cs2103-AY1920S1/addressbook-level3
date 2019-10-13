package calofit.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import calofit.commons.core.GuiSettings;
import calofit.commons.core.LogsCenter;
import calofit.commons.util.CollectionUtil;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Statistics;

/**
 * Represents the in-memory model of the dish database data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static Statistics statistics;

    private final DishDatabase dishDatabase;
    private final MealLog mealLog;
    private final UserPrefs userPrefs;
    private final FilteredList<Dish> filteredDishes;
    private final FilteredList<Meal> filteredMeals;
    private final SortedList<Meal> sortedMeals;

    private final CalorieBudget budget;

    /**
     * Initializes a ModelManager with the given dishDatabase and userPrefs.
     */
    public ModelManager(MealLog mealLog, ReadOnlyDishDatabase dishDatabase, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(dishDatabase, userPrefs);

        logger.fine("Initializing with dish database: " + dishDatabase + " and user prefs " + userPrefs);

        this.dishDatabase = new DishDatabase(dishDatabase);
        this.userPrefs = new UserPrefs(userPrefs);
        this.mealLog = mealLog;
        this.filteredDishes = new FilteredList<>(this.dishDatabase.getDishList());
        this.filteredMeals = new FilteredList<>(this.mealLog.getMeals());
        this.sortedMeals = new SortedList<>(filteredMeals, Comparator.naturalOrder());
        this.statistics = null;
        this.budget = new CalorieBudget();
    }

    public ModelManager() {
        this(new MealLog(), new DishDatabase(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDishDatabase dishDatabase, ReadOnlyUserPrefs userPrefs) {
        this(new MealLog(), dishDatabase, userPrefs);
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
    public Path getDishDatabaseFilePath() {
        return userPrefs.getDishDatabaseFilePath();
    }

    @Override
    public void setDishDatabaseFilePath(Path dishDatabaseFilePath) {
        requireNonNull(dishDatabaseFilePath);
        userPrefs.setDishDatabaseFilePath(dishDatabaseFilePath);
    }

    //=========== DishDatabase ================================================================================

    @Override
    public void setDishDatabase(ReadOnlyDishDatabase dishDatabase) {
        this.dishDatabase.resetData(dishDatabase);
    }

    @Override
    public ReadOnlyDishDatabase getDishDatabase() {
        return dishDatabase;
    }

    @Override
    public boolean hasDish(Dish dish) {
        requireNonNull(dish);
        return dishDatabase.hasDish(dish);
    }

    @Override
    public boolean hasDishName(Dish dish) {
        requireNonNull(dish);
        return dishDatabase.hasDishName(dish);
    }

    @Override
    public Dish getDishByName(Dish dish) {
        return dishDatabase.getDishByName(dish);
    }

    @Override
    public void deleteDish(Dish target) {
        dishDatabase.removeDish(target);
    }

    @Override
    public void addDish(Dish dish) {
        dishDatabase.addDish(dish);
        updateFilteredDishList(PREDICATE_SHOW_ALL_DISHES);
    }

    @Override
    public void setDish(Dish target, Dish editedDish) {
        CollectionUtil.requireAllNonNull(target, editedDish);

        dishDatabase.setDish(target, editedDish);
    }

    //=========== Filtered Dish List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Dish} backed by the internal list of
     * {@code dishDatabase}
     */
    @Override
    public ObservableList<Dish> getFilteredDishList() {
        return filteredDishes;
    }

    @Override
    public void updateFilteredDishList(Predicate<Dish> predicate) {
        requireNonNull(predicate);
        filteredDishes.setPredicate(predicate);
    }

    @Override
    public ObservableList<Meal> getFilteredMealList() {
        return sortedMeals;
    }

    @Override
    public void updateStatistics() {
        this.statistics = Statistics.generateStatistics(this.mealLog);
    }

    @Override
    public Statistics getStatistics() {
        return this.statistics;
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
        return dishDatabase.equals(other.dishDatabase)
                && userPrefs.equals(other.userPrefs)
                && filteredDishes.equals(other.filteredDishes);
    }

    @Override
    public void addMeal(Meal meal) {
        this.mealLog.addMeal(meal);
    }

    @Override
    public MealLog getMealLog() {
        return this.mealLog;
    }

    @Override
    public CalorieBudget getCalorieBudget() {
        return this.budget;
    }
}
