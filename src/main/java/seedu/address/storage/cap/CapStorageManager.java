package seedu.address.storage.cap;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.ReadOnlyUserPrefs;

/**
 * Manages storage of CapLog data in local storage.
 */
public class CapStorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(CapStorageManager.class);
    private CapStorage capStorage;
    private UserPrefsStorage userPrefsStorage;


    public CapStorageManager(CapStorage capLogStorage, UserPrefsStorage userPrefsStorage) {
        super();
        requireAllNonNull(capLogStorage, userPrefsStorage);

        logger.fine("Initializing with cap storage: " + capLogStorage + " and user prefs " + userPrefsStorage);
        this.capStorage = capLogStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<CapUserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CapLog methods ==============================

    @Override
    public Path getCapLogFilePath() {
        return capStorage.getCapLogFilePath();
    }

    @Override
    public Optional<ReadOnlyCapLog> readCapLog() throws DataConversionException, IOException {
        return readCapLog(capStorage.getCapLogFilePath());
    }

    @Override
    public Optional<ReadOnlyCapLog> readCapLog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return capStorage.readCapLog(filePath);
    }

    @Override
    public void saveCapLog(ReadOnlyCapLog addressBook) throws IOException {
        saveCapLog(addressBook, capStorage.getCapLogFilePath());
    }

    @Override
    public void saveCapLog(ReadOnlyCapLog addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        capStorage.saveCapLog(addressBook, filePath);
    }

}
