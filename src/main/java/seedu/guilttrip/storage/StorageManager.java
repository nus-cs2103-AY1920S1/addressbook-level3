package seedu.guilttrip.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.DataConversionException;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.ReadOnlyUserPrefs;
import seedu.guilttrip.model.UserPrefs;

/**
 * Manages storage of GuiltTrip data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GuiltTripStorage guiltTripStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GuiltTripStorage guiltTripStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.guiltTripStorage = guiltTripStorage;
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


    // ================ GuiltTrip methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return guiltTripStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyGuiltTrip> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(guiltTripStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGuiltTrip> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return guiltTripStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyGuiltTrip addressBook) throws IOException {
        saveAddressBook(addressBook, guiltTripStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyGuiltTrip addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        guiltTripStorage.saveAddressBook(addressBook, filePath);
    }

}
