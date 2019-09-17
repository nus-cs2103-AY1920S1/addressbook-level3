package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyExpiryDateTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExpiryDateTrackerStorage expiryDateTrackerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ExpiryDateTrackerStorage expiryDateTrackerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.expiryDateTrackerStorage = expiryDateTrackerStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getExpiryDateTrackerFilePath() {
        return expiryDateTrackerStorage.getExpiryDateTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker() throws DataConversionException, IOException {
        return readExpiryDateTracker(expiryDateTrackerStorage.getExpiryDateTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker(Path filePath) throws
            DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expiryDateTrackerStorage.readExpiryDateTracker(filePath);
    }

    @Override
    public void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker) throws IOException {
        saveExpiryDateTracker(expiryDateTracker, expiryDateTrackerStorage.getExpiryDateTrackerFilePath());
    }

    @Override
    public void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expiryDateTrackerStorage.saveExpiryDateTracker(expiryDateTracker, filePath);
    }

}
