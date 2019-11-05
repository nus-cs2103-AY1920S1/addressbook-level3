package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of BankAccount data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserStateStorage userStateStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(UserStateStorage userStateStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.userStateStorage = userStateStorage;
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


    // ================ BankAccount methods ==============================

    @Override
    public Path getUserStateFilePath() {
        return userStateStorage.getUserStateFilePath();
    }

    @Override
    public Optional<ReadOnlyUserState> readUserState() throws DataConversionException, IOException {
        return readUserState(userStateStorage.getUserStateFilePath());
    }

    @Override
    public Optional<ReadOnlyUserState> readUserState(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userStateStorage.readUserState(filePath);
    }

    @Override
    public void saveUserState(ReadOnlyUserState userState) throws IOException {
        saveUserState(userState, userStateStorage.getUserStateFilePath());
    }

    @Override
    public void saveUserState(ReadOnlyUserState userState, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userStateStorage.saveUserState(userState, filePath);
    }

}
