package seedu.moneygowhere.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.ReadOnlyUserPrefs;
import seedu.moneygowhere.model.UserPrefs;

/**
 * Manages storage of SpendingBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SpendingBookStorage spendingBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SpendingBookStorage spendingBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.spendingBookStorage = spendingBookStorage;
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


    // ================ SpendingBook methods ==============================

    @Override
    public Path getSpendingBookFilePath() {
        return spendingBookStorage.getSpendingBookFilePath();
    }

    @Override
    public Optional<ReadOnlySpendingBook> readSpendingBook() throws DataConversionException, IOException {
        return readSpendingBook(spendingBookStorage.getSpendingBookFilePath());
    }

    @Override
    public Optional<ReadOnlySpendingBook> readSpendingBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return spendingBookStorage.readSpendingBook(filePath);
    }

    @Override
    public void saveSpendingBook(ReadOnlySpendingBook spendingBook) throws IOException {
        saveSpendingBook(spendingBook, spendingBookStorage.getSpendingBookFilePath());
    }

    @Override
    public void saveSpendingBook(ReadOnlySpendingBook spendingBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        spendingBookStorage.saveSpendingBook(spendingBook, filePath);
    }

}
