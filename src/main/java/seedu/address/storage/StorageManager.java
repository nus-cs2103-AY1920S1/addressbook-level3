package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.NusModsData;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TimeBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private TimeBookStorage timeBookStorage;
    private NusModsDataStorage nusModsDataStorage;


    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage, TimeBookStorage timeBookStorage,
                          NusModsDataStorage nusModsDataStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.timeBookStorage = timeBookStorage;
        this.nusModsDataStorage = nusModsDataStorage;
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

    @Override
    public Path getTimeBookFilePath() {
        return null;
    }

    @Override
    public Optional<TimeBook> readTimeBook() throws DataConversionException, IOException {
        return readTimeBook(timeBookStorage.getTimeBookFilePath());
    }

    @Override
    public Optional<TimeBook> readTimeBook(Path filePath) throws DataConversionException, IOException {
        return timeBookStorage.readTimeBook(filePath);
    }

    @Override
    public void saveTimeBook(TimeBook timeBook) throws IOException {
        saveTimeBook(timeBook, timeBookStorage.getTimeBookFilePath());
    }

    @Override
    public void saveTimeBook(TimeBook timeBook, Path filePath) throws IOException {
        timeBookStorage.saveTimeBook(timeBook, filePath);
    }

    // ================ NusModsData methods ==============================

    @Override
    public Path getNusModsDataFilePath() {
        return nusModsDataStorage.getNusModsDataFilePath();
    }

    @Override
    public Optional<NusModsData> readNusModsData() throws DataConversionException, IOException {
        return readNusModsData(nusModsDataStorage.getNusModsDataFilePath());
    }

    @Override
    public Optional<NusModsData> readNusModsData(Path filePath) throws DataConversionException, IOException {
        return nusModsDataStorage.readNusModsData(filePath);
    }

    @Override
    public void saveNusModsData(NusModsData nusModsData) throws IOException {
        saveNusModsData(nusModsData, nusModsDataStorage.getNusModsDataFilePath());
    }

    @Override
    public void saveNusModsData(NusModsData nusModsData, Path filePath) throws IOException {
        nusModsDataStorage.saveNusModsData(nusModsData, filePath);
    }

}
