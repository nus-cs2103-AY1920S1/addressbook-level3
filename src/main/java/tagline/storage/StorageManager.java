package tagline.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tagline.commons.core.LogsCenter;
import tagline.commons.exceptions.DataConversionException;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.UserPrefs;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.storage.contact.AddressBookStorage;
import tagline.storage.group.GroupBookStorage;
import tagline.storage.note.NoteBookStorage;
import tagline.storage.tag.TagBookStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private NoteBookStorage noteBookStorage;
    private GroupBookStorage groupBookStorage;
    private TagBookStorage tagBookStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(AddressBookStorage addressBookStorage, NoteBookStorage noteBookStorage,
                          GroupBookStorage groupBookStorage, TagBookStorage tagBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.noteBookStorage = noteBookStorage;
        this.groupBookStorage = groupBookStorage;
        this.tagBookStorage = tagBookStorage;
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

    // ================ GroupBook methods ==============================

    @Override
    public Path getGroupBookFilePath() {
        return groupBookStorage.getGroupBookFilePath();
    }

    @Override
    public Optional<ReadOnlyGroupBook> readGroupBook() throws DataConversionException, IOException {
        return readGroupBook(groupBookStorage.getGroupBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGroupBook> readGroupBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return groupBookStorage.readGroupBook(filePath);
    }

    @Override
    public void saveGroupBook(ReadOnlyGroupBook groupBook) throws IOException {
        saveGroupBook(groupBook, groupBookStorage.getGroupBookFilePath());
    }

    @Override
    public void saveGroupBook(ReadOnlyGroupBook groupBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        groupBookStorage.saveGroupBook(groupBook, filePath);
    }

    // ================ TagBook methods ==============================

    @Override
    public Path getTagBookFilePath() {
        return tagBookStorage.getTagBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTagBook> readTagBook() throws DataConversionException, IOException {
        return readTagBook(tagBookStorage.getTagBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTagBook> readTagBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tagBookStorage.readTagBook(filePath);
    }

    @Override
    public void saveTagBook(ReadOnlyTagBook tagBook) throws IOException {
        saveTagBook(tagBook, tagBookStorage.getTagBookFilePath());
    }

    @Override
    public void saveTagBook(ReadOnlyTagBook tagBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tagBookStorage.saveTagBook(tagBook, filePath);
    }
}
