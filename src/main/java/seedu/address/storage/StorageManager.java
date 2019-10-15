package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHealthRecords;
import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of DukeCooks data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserProfileStorage userProfileStorage;
    private HealthRecordsStorage healthRecordsStorage;
    private RecipeBookStorage recipeBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(UserProfileStorage userProfileStorage, HealthRecordsStorage healthRecordsStorage,
                          RecipeBookStorage recipeBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.userProfileStorage = userProfileStorage;
        this.healthRecordsStorage = healthRecordsStorage;
        this.recipeBookStorage = recipeBookStorage;
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


    // ================ UserProfile methods ==============================

    @Override
    public Path getUserProfileFilePath() {
        return userProfileStorage.getUserProfileFilePath();
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException {
        return readUserProfile(userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userProfileStorage.readUserProfile(filePath);
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile dukeCooks) throws IOException {
        saveUserProfile(dukeCooks, userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile dukeCooks, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userProfileStorage.saveUserProfile(dukeCooks, filePath);
    }

    // ================ Health Records methods ==============================

    @Override
    public Path getHealthRecordsFilePath() {
        return healthRecordsStorage.getHealthRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException, IOException {
        return readHealthRecords(healthRecordsStorage.getHealthRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthRecordsStorage.readHealthRecords(filePath);
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException {
        saveHealthRecords(healthRecords, healthRecordsStorage.getHealthRecordsFilePath());
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthRecordsStorage.saveHealthRecords(healthRecords, filePath);
    }

    // ================ RecipeBook methods ==============================

    @Override
    public Path getRecipesFilePath() {
        return recipeBookStorage.getRecipesFilePath();
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException, IOException {
        return readRecipeBook(recipeBookStorage.getRecipesFilePath());
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return recipeBookStorage.readRecipeBook(filePath);
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook recipeBook) throws IOException {
        saveRecipeBook(recipeBook, recipeBookStorage.getRecipesFilePath());
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook recipeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        recipeBookStorage.saveRecipeBook(recipeBook, filePath);
    }
}
