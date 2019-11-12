package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCardBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private FileBookStorage fileBookStorage;
    private CardBookStorage cardBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private NoteBookStorage noteBookStorage;
    private PasswordBookStorage passwordBookStorage;
    private String password;

    public StorageManager(AddressBookStorage addressBookStorage,
                          FileBookStorage fileBookStorage,
                          CardBookStorage cardBookStorage,
                          NoteBookStorage noteBookStorage,
                          PasswordBookStorage passwordBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          String password) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.fileBookStorage = fileBookStorage;
        this.cardBookStorage = cardBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.noteBookStorage = noteBookStorage;
        this.passwordBookStorage = passwordBookStorage;
        this.password = password;
    }

    @Override
    public String getStoragePassword() {
        return password;
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

    // ================ FileBook methods ==============================

    @Override
    public Path getFileBookFilePath() {
        return fileBookStorage.getFileBookFilePath();
    }

    @Override
    public Optional<ReadOnlyFileBook> readFileBook() throws DataConversionException, IOException {
        return readFileBook(fileBookStorage.getFileBookFilePath());
    }

    @Override
    public Optional<ReadOnlyFileBook> readFileBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return fileBookStorage.readFileBook(filePath);
    }

    @Override
    public void saveFileBook(ReadOnlyFileBook fileBook) throws IOException {
        saveFileBook(fileBook, fileBookStorage.getFileBookFilePath());
    }

    @Override
    public void saveFileBook(ReadOnlyFileBook fileBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        fileBookStorage.saveFileBook(fileBook, filePath);
    }

    // ================ CardBook methods ==============================

    @Override
    public Path getCardBookFilePath() {
        return cardBookStorage.getCardBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCardBook> readCardBook() throws DataConversionException, IOException {
        return readCardBook(cardBookStorage.getCardBookFilePath());
    }

    @Override
    public Optional<ReadOnlyCardBook> readCardBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cardBookStorage.readCardBook(filePath);
    }

    @Override
    public void saveCardBook(ReadOnlyCardBook cardBook) throws IOException {
        saveCardBook(cardBook, cardBookStorage.getCardBookFilePath());
    }

    @Override
    public void saveCardBook(ReadOnlyCardBook cardBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cardBookStorage.saveCardBook(cardBook, filePath);
    }
    // ================ NoteBook methods ==============================

    @Override
    public Path getNoteBookFilePath() {
        return noteBookStorage.getNoteBookFilePath();
    }

    @Override
    public Optional<ReadOnlyNoteBook> readNoteBook() throws DataConversionException, IOException {
        return readNoteBook(noteBookStorage.getNoteBookFilePath());
    }

    @Override
    public Optional<ReadOnlyNoteBook> readNoteBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return noteBookStorage.readNoteBook(filePath);
    }

    @Override
    public void saveNoteBook(ReadOnlyNoteBook noteBook) throws IOException {
        saveNoteBook(noteBook, noteBookStorage.getNoteBookFilePath());
    }

    @Override
    public void saveNoteBook(ReadOnlyNoteBook noteBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        noteBookStorage.saveNoteBook(noteBook, filePath);
    }
    // ================ PasswordBook methods ==============================

    @Override
    public Path getPasswordBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyPasswordBook> readPasswordBook() throws DataConversionException, IOException {
        return readPasswordBook(passwordBookStorage.getPasswordBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPasswordBook> readPasswordBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return passwordBookStorage.readPasswordBook(filePath);
    }

    @Override
    public void savePasswordBook(ReadOnlyPasswordBook passwordBook) throws IOException {
        savePasswordBook(passwordBook, passwordBookStorage.getPasswordBookFilePath());
    }

    @Override
    public void savePasswordBook(ReadOnlyPasswordBook passwordBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        passwordBookStorage.savePasswordBook(passwordBook, filePath);
    }

}
