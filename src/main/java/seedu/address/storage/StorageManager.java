package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyIncidentManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of IncidentManager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private IncidentManagerStorage incidentManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(IncidentManagerStorage incidentManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.incidentManagerStorage = incidentManagerStorage;
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


    // ================ IncidentManager methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return incidentManagerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyIncidentManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(incidentManagerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyIncidentManager> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return incidentManagerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyIncidentManager addressBook) throws IOException {
        saveAddressBook(addressBook, incidentManagerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyIncidentManager addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        incidentManagerStorage.saveAddressBook(addressBook, filePath);
    }

}
