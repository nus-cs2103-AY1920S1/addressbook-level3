package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.UserPrefs;

/**
 * Manages storage of Weme data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WemeStorage wemeStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(WemeStorage wemeStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.wemeStorage = wemeStorage;
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


    // ================ Weme methods ==============================

    @Override
    public Path getWemeFilePath() {
        return wemeStorage.getWemeFilePath();
    }

    @Override
    public Optional<ReadOnlyWeme> readWeme() throws DataConversionException, IOException {
        return readWeme(wemeStorage.getWemeFilePath());
    }

    @Override
    public Optional<ReadOnlyWeme> readWeme(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return wemeStorage.readWeme(filePath);
    }

    @Override
    public void saveWeme(ReadOnlyWeme weme) throws IOException {
        saveWeme(weme, wemeStorage.getWemeFilePath());
    }

    @Override
    public void saveWeme(ReadOnlyWeme weme, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wemeStorage.saveWeme(weme, filePath);
    }

}
