package calofit.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import calofit.commons.core.GuiSettings;
import calofit.commons.core.LogsCenter;
import calofit.commons.util.CollectionUtil;
import calofit.commons.util.ObservableUtil;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.Name;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.meal.ReadOnlyMealLog;
import calofit.model.util.Statistics;

/**
 * Represents the in-memory model of the dish database data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DishDatabase dishDatabase;
    private final MealLog mealLog;
    private final UserPrefs userPrefs;
    private final FilteredList<Dish> filteredDishes;
    private final CalorieBudget budget;
    private ObjectExpression<Predicate<Dish>> suggestedDishFilter;

    private ObjectProperty<LocalDateTime> nowProperty = new SimpleObjectProperty<>(LocalDateTime.now());

    /**
     * Initializes a ModelManager with the given dishDatabase and userPrefs.
     */
    public ModelManager(ReadOnlyMealLog mealLog, ReadOnlyDishDatabase dishDatabase,
                        ReadOnlyUserPrefs userPrefs, CalorieBudget budget) {
        CollectionUtil.requireAllNonNull(dishDatabase, userPrefs, budget);

        logger.fine("Initializing with dish database: " + dishDatabase + " and user prefs " + userPrefs);

        this.dishDatabase = new DishDatabase(dishDatabase);
        this.userPrefs = new UserPrefs(userPrefs);
        this.mealLog = new MealLog(mealLog);
        this.filteredDishes = new FilteredList<>(this.dishDatabase.getDishList());
        this.budget = budget;
        DoubleExpression remainingCalories = budget.currentBudget().subtract(this.mealLog.getTodayCalories());
        suggestedDishFilter = ObservableUtil.mapToObject(remainingCalories,
            remain -> dish -> dish.getCalories().getValue() <= remain);
        filteredDishes.predicateProperty().bind(suggestedDishFilter);

        ObjectExpression<LocalDate> todayExp = ObservableUtil.cachingMap(nowProperty, LocalDateTime::toLocalDate);
        this.mealLog.todayProperty().bind(todayExp);
        this.budget.todayProperty().bind(todayExp);
    }

    public ModelManager() {
        this(new MealLog(), new DishDatabase(), new UserPrefs(), new CalorieBudget());
    }

    public ModelManager(ReadOnlyDishDatabase dishDatabase, ReadOnlyUserPrefs userPrefs) {
        this(new MealLog(), dishDatabase, userPrefs, new CalorieBudget());
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
    public Dish getDish(Dish dish) {
        return dishDatabase.getDish(dish);
    }

    @Override
    public boolean hasDishName(Name dishName) {
        requireNonNull(dishName);
        return dishDatabase.hasDishName(dishName);
    }

    @Override
    public Dish getDishByName(Name dishName) {
        return dishDatabase.getDishByName(dishName);
    }

    @Override
    public void deleteDish(Dish target) {
        dishDatabase.removeDish(target);
    }

    @Override
    public void addDish(Dish dish) {
        dishDatabase.addDish(dish);
        setDishFilterPredicate(PREDICATE_SHOW_DEFAULT);
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
    public void setDishFilterPredicate(Predicate<Dish> predicate) {
        if (filteredDishes.predicateProperty().isBound()) {
            filteredDishes.predicateProperty().unbind();
        }

        if (predicate == null) {
            filteredDishes.predicateProperty().bind(suggestedDishFilter);
        } else {
            filteredDishes.setPredicate(predicate);
        }
    }

    @Override
    public ObservableList<Meal> getTodayMealList() {
        return this.mealLog.getTodayMeals();
    }

    @Override
    public Statistics getStatistics() {
        return Statistics.generateStatistics(
                this.mealLog.getCurrentMonthMeals(), this.getCalorieBudget());
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
                // && mealLog.equals(other.mealLog);
                && filteredDishes.equals(other.filteredDishes);

    }

    @Override
    public void addMeal(Meal meal) {
        this.mealLog.addMeal(meal);
    }

    @Override
    public void removeMeal(Meal meal) {
        mealLog.removeMeal(meal);
    }

    @Override
    public void setMeal(Meal target, Meal editedMeal) {
        CollectionUtil.requireAllNonNull(target, editedMeal);

        mealLog.setMeal(target, editedMeal);
    }

    @Override
    public MealLog getMealLog() {
        return this.mealLog;
    }

    @Override
    public CalorieBudget getCalorieBudget() {
        return this.budget;
    }

    @Override
    public int getRemainingCalories() {
        int remainingBudget = budget.getCurrentBudget().orElse(0);
        for (Meal meal : mealLog.getTodayMeals()) {
            remainingBudget -= meal.getDish().getCalories().getValue();
        }

        return remainingBudget;
    }

    @Override
    public ObjectProperty<LocalDateTime> nowProperty() {
        return nowProperty;
    }
}
