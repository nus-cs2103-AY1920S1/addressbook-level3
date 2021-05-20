package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of FinSec data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FinSecStorage finSecStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FinSecStorage finSecStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.finSecStorage = finSecStorage;
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


    // ================ FinSec methods ==============================

    @Override
    public Path getFinSecFilePath() {
        return finSecStorage.getFinSecFilePath();
    }

    @Override
    public Optional<ReadOnlyFinSec> readContacts() throws DataConversionException, IOException {
        return readContacts(finSecStorage.getFinSecFilePath());
    }

    @Override
    public Optional<ReadOnlyFinSec> readContacts(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return finSecStorage.readContacts(filePath);
    }

    @Override
    public void saveFinSec(ReadOnlyFinSec contact) throws IOException {
        saveFinSec(contact, finSecStorage.getFinSecFilePath());
    }

    @Override
    public void saveFinSec(ReadOnlyFinSec contact, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        finSecStorage.saveFinSec(contact, filePath);
    }

}
