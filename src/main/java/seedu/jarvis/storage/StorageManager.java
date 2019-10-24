package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.storage.address.AddressBookStorage;
import seedu.jarvis.storage.cca.CcaTrackerStorage;
import seedu.jarvis.storage.history.HistoryManagerStorage;
import seedu.jarvis.storage.userprefs.UserPrefsStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private HistoryManagerStorage historyManagerStorage;
    private CcaTrackerStorage ccaTrackerStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          HistoryManagerStorage historyManagerStorage, CcaTrackerStorage ccaTrackerStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.historyManagerStorage = historyManagerStorage;
        this.ccaTrackerStorage = ccaTrackerStorage;
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

    // ================ HistoryManager methods ===========================


    /**
     * Gets the file path of the data file for {@code HistoryManager}.
     *
     * @return File path of the data file for {@code HistoryManager}.
     */
    @Override
    public Path getHistoryManagerFilePath() {
        return historyManagerStorage.getHistoryManagerFilePath();
    }

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<HistoryManager> readHistoryManager() throws DataConversionException, IOException {
        return historyManagerStorage.readHistoryManager(historyManagerStorage.getHistoryManagerFilePath());
    }

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code HistoryManager} data.
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<HistoryManager> readHistoryManager(Path filePath) throws DataConversionException, IOException {
        return historyManagerStorage.readHistoryManager(filePath);
    }

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveHistoryManager(HistoryManager historyManager) throws IOException {
        saveHistoryManager(historyManager, historyManagerStorage.getHistoryManagerFilePath());
    }

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @param filePath       {@code Path} to read {@code HistoryManager} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveHistoryManager(HistoryManager historyManager, Path filePath) throws IOException {
        historyManagerStorage.saveHistoryManager(historyManager, filePath);
    }

    // ================ CcaTracker methods ===============================


    /**
     * Gets the file path of the data file for {@code CcaTracker}.
     *
     * @return File path of the data file for {@code CcaTracker}.
     */
    @Override
    public Path getCcaTrackerFilePath() {
        return ccaTrackerStorage.getCcaTrackerFilePath();
    }

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CcaTracker> readCcaTracker() throws DataConversionException, IOException {
        return ccaTrackerStorage.readCcaTracker(ccaTrackerStorage.getCcaTrackerFilePath());
    }

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CcaTracker} data.
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CcaTracker> readCcaTracker(Path filePath) throws DataConversionException, IOException {
        return ccaTrackerStorage.readCcaTracker(filePath);
    }

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCcaTracker(CcaTracker ccaTracker) throws IOException {
        ccaTrackerStorage.saveCcaTracker(ccaTracker, ccaTrackerStorage.getCcaTrackerFilePath());
    }

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @param filePath   {@code Path} to read {@code CcaTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCcaTracker(CcaTracker ccaTracker, Path filePath) throws IOException {
        ccaTrackerStorage.saveCcaTracker(ccaTracker, filePath);
    }

}
