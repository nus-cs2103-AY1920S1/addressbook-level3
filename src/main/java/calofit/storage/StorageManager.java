package calofit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import calofit.commons.core.LogsCenter;
import calofit.commons.exceptions.DataConversionException;
import calofit.model.CalorieBudget;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.ReadOnlyMealLog;

/**
 * Manages storage of DishDatabase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DishDatabaseStorage dishDatabaseStorage;
    private MealLogStorage mealLogStorage;
    private UserPrefsStorage userPrefsStorage;
    private CalorieBudgetStorage calorieBudgetStorage;


    public StorageManager(DishDatabaseStorage dishDatabaseStorage,
                          MealLogStorage mealLogStorage,
                          UserPrefsStorage userPrefsStorage,
                          CalorieBudgetStorage calorieBudgetStorage) {
        super();
        this.dishDatabaseStorage = dishDatabaseStorage;
        this.mealLogStorage = mealLogStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.calorieBudgetStorage = calorieBudgetStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ DishDatabase methods ==============================

    @Override
    public Path getDishDatabaseFilePath() {
        return dishDatabaseStorage.getDishDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyDishDatabase> readDishDatabase() throws DataConversionException, IOException {
        return readDishDatabase(dishDatabaseStorage.getDishDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyDishDatabase> readDishDatabase(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return dishDatabaseStorage.readDishDatabase(filePath);
    }

    @Override
    public void saveDishDatabase(ReadOnlyDishDatabase dishDatabase) throws IOException {
        saveDishDatabase(dishDatabase, dishDatabaseStorage.getDishDatabaseFilePath());
    }

    @Override
    public void saveDishDatabase(ReadOnlyDishDatabase dishDatabase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        dishDatabaseStorage.saveDishDatabase(dishDatabase, filePath);
    }

    // ================ Meallog methods ==============================


    @Override
    public Path getMealLogFilePath() {
        return dishDatabaseStorage.getDishDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyMealLog> readMealLog() throws DataConversionException, IOException {
        return readMealLog(mealLogStorage.getMealLogFilePath());
    }

    @Override
    public Optional<ReadOnlyMealLog> readMealLog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return mealLogStorage.readMealLog(filePath);
    }

    @Override
    public void saveMealLog(ReadOnlyMealLog mealLog) throws IOException {
        saveMealLog(mealLog, mealLogStorage.getMealLogFilePath());
    }

    @Override
    public void saveMealLog(ReadOnlyMealLog mealLog, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        mealLogStorage.saveMealLog(mealLog, filePath);
    }
    // CalorieBudget methods

    @Override
    public Path getCalorieBudgetFilePath() {
        return calorieBudgetStorage.getCalorieBudgetFilePath();
    }

    @Override
    public Optional<CalorieBudget> readCalorieBudget() throws DataConversionException, IOException {
        return readCalorieBudget(calorieBudgetStorage.getCalorieBudgetFilePath());
    }

    @Override
    public Optional<CalorieBudget> readCalorieBudget(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return calorieBudgetStorage.readCalorieBudget(filePath);
    }

    @Override
    public void saveCalorieBudget(CalorieBudget budget) throws IOException {
        saveCalorieBudget(budget, calorieBudgetStorage.getCalorieBudgetFilePath());
    }

    @Override
    public void saveCalorieBudget(CalorieBudget budget, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        calorieBudgetStorage.saveCalorieBudget(budget, filePath);
    }
}
