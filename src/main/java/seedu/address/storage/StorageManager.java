package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
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
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getFlashcardFilePath(), addressBookStorage.getNoteFilePath(),
                addressBookStorage.getCheatSheetFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path flashcardFilePath, Path noteFilePath,
                                                         Path cheatsheetFilePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from files: " + flashcardFilePath + ", " + noteFilePath + ", " + cheatsheetFilePath);
        return addressBookStorage.readAddressBook(flashcardFilePath, noteFilePath, cheatsheetFilePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getFlashcardFilePath(), getNoteFilePath(), getCheatSheetFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path flashcardFilePath, Path noteFilePath,
                                Path cheatsheetFilePath) throws IOException {
        logger.fine("Attempting to write to data files: " + flashcardFilePath + ", " + noteFilePath + ", " + cheatsheetFilePath);
        addressBookStorage.saveAddressBook(addressBook, flashcardFilePath, noteFilePath, cheatsheetFilePath);
    }

    // ================ CheatSheet methods ==============================

    @Override
    public Path getCheatSheetFilePath() {
        return addressBookStorage.getCheatSheetFilePath();
    }

    // ================ Flashcard methods ==============================
    @Override
    public Path getFlashcardFilePath() {
        return addressBookStorage.getFlashcardFilePath();
    }

    // ================ Note methods ==============================
    @Override
    public Path getNoteFilePath() {
        return addressBookStorage.getNoteFilePath();
    }

}
