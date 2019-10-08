package calofit.storage;

import calofit.commons.core.LogsCenter;
import calofit.commons.exceptions.DataConversionException;
import calofit.model.ReadOnlyDishDatabase;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of DishDatabase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DishDatabaseStorage dishDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(DishDatabaseStorage dishDatabaseStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.dishDatabaseStorage = dishDatabaseStorage;
        this.userPrefsStorage = userPrefsStorage;
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

}
