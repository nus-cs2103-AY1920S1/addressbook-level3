package seedu.revision.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.revision.commons.core.LogsCenter;
import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.ReadOnlyUserPrefs;
import seedu.revision.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private HistoryStorage historyStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          HistoryStorage historyStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.historyStorage = historyStorage;
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
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ History methods ==============================

    @Override
    public Path getHistoryFilePath() {
        return historyStorage.getHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlyHistory> readHistory() throws DataConversionException, IOException {
        return readHistory(historyStorage.getHistoryFilePath());
    }

    @Override
    public Optional<ReadOnlyHistory> readHistory(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return historyStorage.readHistory(filePath);
    }

    @Override
    public void saveHistory(ReadOnlyHistory history) throws IOException {
        saveHistory(history, historyStorage.getHistoryFilePath());
    }

    @Override
    public void saveHistory(ReadOnlyHistory history, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        historyStorage.saveHistory(history, filePath);
    }

}
