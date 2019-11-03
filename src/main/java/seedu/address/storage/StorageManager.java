package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ActivityBook;
import seedu.address.model.InternalState;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
* Manages storage of AddressBook data in local storage.
*/
public class StorageManager implements Storage {
    private static final Logger logger = LogsCenter.getLogger(
        StorageManager.class
    );
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private ActivityBookStorage activityBookStorage;
    private InternalStateStorage internalStateStorage;

    public StorageManager(
        AddressBookStorage addressBookStorage,
        UserPrefsStorage userPrefsStorage,
        InternalStateStorage internalStateStorage,
        ActivityBookStorage activityBookStorage
    ) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.activityBookStorage = activityBookStorage;
        this.internalStateStorage = internalStateStorage;
    }

    // ================ InternalState methods ===========================
    @Override
    public Path getInternalStateFilePath() {
        return internalStateStorage.getInternalStateFilePath();
    }

    @Override
    public Optional<InternalState> readInternalState()
        throws DataConversionException, IOException {
        return internalStateStorage.readInternalState();
    }

    @Override
    public void saveInternalState(InternalState internalState)
        throws IOException {
        internalStateStorage.saveInternalState(internalState);
    }

    // ================ UserPrefs methods ==============================
    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs()
        throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AddressBook methods ==============================
    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook()
        throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook)
        throws IOException {
        saveAddressBook(
            addressBook,
            addressBookStorage.getAddressBookFilePath()
        );
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
        throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }
    // ================ ActivityBook methods ==============================

    @Override
    public Path getActivityBookFilePath() {
        return activityBookStorage.getActivityBookFilePath();
    }

    @Override
    public Optional<ActivityBook> readActivityBook() throws DataConversionException, IOException {
        return activityBookStorage.readActivityBook();
    }

    @Override
    public void saveActivityBook(ActivityBook activityBook) throws IOException {
        activityBookStorage.saveActivityBook(activityBook);
    }
}
