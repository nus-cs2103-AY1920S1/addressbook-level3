package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.UserPrefs;

/**
 * Manages storage of MemeBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WemeStorage wemeStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(WemeStorage wemeStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.wemeStorage = wemeStorage;
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
        return wemeStorage.getMemeBookFilePath();
    }

    @Override
    public Optional<ReadOnlyMemeBook> readWeme() throws DataConversionException, IOException {
        return readWeme(wemeStorage.getMemeBookFilePath());
    }

    @Override
    public Optional<ReadOnlyMemeBook> readWeme(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return wemeStorage.readWeme(filePath);
    }

    @Override
    public void saveWeme(ReadOnlyMemeBook memeBook) throws IOException {
        saveWeme(memeBook, wemeStorage.getMemeBookFilePath());
    }

    @Override
    public void saveWeme(ReadOnlyMemeBook memeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wemeStorage.saveWeme(memeBook, filePath);
    }

}
