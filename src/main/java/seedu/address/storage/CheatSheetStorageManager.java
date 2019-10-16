package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCheatSheetBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CheatSheetBook data in local storage.
 */

public class CheatSheetStorageManager implements CheatSheetStorage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CheatSheetBookStorage cheatSheetBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public CheatSheetStorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.cheatSheetBookStorage = cheatSheetBookStorage;
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

    // ================ CheatSheet methods ==============================

    @Override
    public Path getCheatSheetBookFilePath() {
        return cheatSheetBookStorage.getCheatSheetBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCheatSheetBook> readCheatSheetBook() throws DataConversionException, IOException {
        return readCheatSheetBook(cheatSheetBookStorage.getCheatSheetBookFilePath());
    }

    @Override
    public Optional<ReadOnlyCheatSheetBook> readCheatSheetBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cheatSheetBookStorage.readCheatSheetBook(filePath);
    }

    @Override
    public void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook) throws IOException {
        saveCheatSheetBook(cheatSheetBook, cheatSheetBookStorage.getCheatSheetBookFilePath());
    }

    @Override
    public void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cheatSheetBookStorage.saveCheatSheetBook(cheatSheetBook, filePath);
    }
}
