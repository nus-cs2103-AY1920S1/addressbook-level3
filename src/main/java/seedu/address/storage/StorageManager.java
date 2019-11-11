package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyKeyboardFlashCards;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of KeyboardFlashCards data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private KeyboardFlashCardsStorage keyboardFlashCardsStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(KeyboardFlashCardsStorage keyboardFlashCardsStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.keyboardFlashCardsStorage = keyboardFlashCardsStorage;
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


    // ================ KeyboardFlashCards methods ==============================

    @Override
    public Path getKeyboardFlashCardsFilePath() {
        return keyboardFlashCardsStorage.getKeyboardFlashCardsFilePath();
    }

    @Override
    public Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards() throws DataConversionException, IOException {
        return readKeyboardFlashCards(keyboardFlashCardsStorage.getKeyboardFlashCardsFilePath());
    }

    @Override
    public Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return keyboardFlashCardsStorage.readKeyboardFlashCards(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook) throws IOException {
        saveAddressBook(addressBook, keyboardFlashCardsStorage.getKeyboardFlashCardsFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        keyboardFlashCardsStorage.saveAddressBook(addressBook, filePath);
    }

}
