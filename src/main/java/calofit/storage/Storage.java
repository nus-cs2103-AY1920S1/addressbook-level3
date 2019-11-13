package calofit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.ReadOnlyMealLog;

/**
 * API of the Storage component
 */
public interface Storage extends DishDatabaseStorage, MealLogStorage, UserPrefsStorage, CalorieBudgetStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getDishDatabaseFilePath();

    @Override
    Optional<ReadOnlyDishDatabase> readDishDatabase() throws DataConversionException, IOException;

    @Override
    void saveDishDatabase(ReadOnlyDishDatabase dishDatabase) throws IOException;

    @Override
    Path getMealLogFilePath();

    @Override
    Optional<ReadOnlyMealLog> readMealLog() throws DataConversionException, IOException;

    @Override
    void saveMealLog(ReadOnlyMealLog mealLog) throws IOException;
}
