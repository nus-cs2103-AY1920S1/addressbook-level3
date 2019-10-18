package seedu.address.storage.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.ReadOnlyModulo;
import seedu.address.model.cap.ReadOnlyUserPrefs;
import seedu.address.model.cap.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class CapStorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(CapStorageManager.class);
    private CapStorage capStorage;
    private UserPrefsStorage userPrefsStorage;


    public CapStorageManager(CapStorage capLogStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.capStorage = capLogStorage;
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
    public Path getCapLogFilePath() {
        return capStorage.getCapLogFilePath();
    }

    @Override
    public Optional<ReadOnlyModulo> readCapLog() throws DataConversionException, IOException {
        return readCapLog(capStorage.getCapLogFilePath());
    }

    @Override
    public Optional<ReadOnlyModulo> readCapLog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return capStorage.readCapLog(filePath);
    }

    @Override
    public void saveCapLog(ReadOnlyModulo addressBook) throws IOException {
        saveCapLog(addressBook, capStorage.getCapLogFilePath());
    }

    @Override
    public void saveCapLog(ReadOnlyModulo addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        capStorage.saveCapLog(addressBook, filePath);
    }

}
