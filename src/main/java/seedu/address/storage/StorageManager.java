package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMemeBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of MemeBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MemeBookStorage memeBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(MemeBookStorage memeBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.memeBookStorage = memeBookStorage;
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


    // ================ MemeBook methods ==============================

    @Override
    public Path getMemeBookFilePath() {
        return memeBookStorage.getMemeBookFilePath();
    }

    @Override
    public Optional<ReadOnlyMemeBook> readMemeBook() throws DataConversionException, IOException {
        return readMemeBook(memeBookStorage.getMemeBookFilePath());
    }

    @Override
    public Optional<ReadOnlyMemeBook> readMemeBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return memeBookStorage.readMemeBook(filePath);
    }

    @Override
    public void saveMemeBook(ReadOnlyMemeBook memeBook) throws IOException {
        saveMemeBook(memeBook, memeBookStorage.getMemeBookFilePath());
    }

    @Override
    public void saveMemeBook(ReadOnlyMemeBook memeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        memeBookStorage.saveMemeBook(memeBook, filePath);
    }

}
