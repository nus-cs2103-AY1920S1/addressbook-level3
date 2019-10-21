package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AppData data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AppDataStorage appDataStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AppDataStorage appDataStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.appDataStorage = appDataStorage;
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


    // ================ AppData methods ==============================

    @Override
    public Path getAppDataFilePath() {
        return appDataStorage.getAppDataFilePath();
    }

    @Override
    public Optional<ReadOnlyAppData> readAppData() throws DataConversionException, IOException {
        return readAppData(appDataStorage.getAppDataFilePath());
    }

    @Override
    public Optional<ReadOnlyAppData> readAppData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return appDataStorage.readAppData(filePath);
    }

    @Override
    public void saveAppData(ReadOnlyAppData appData) throws IOException {
        saveAppData(appData, appDataStorage.getAppDataFilePath());
    }

    @Override
    public void saveAppData(ReadOnlyAppData appData, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        appDataStorage.saveAppData(appData, filePath);
    }

}
