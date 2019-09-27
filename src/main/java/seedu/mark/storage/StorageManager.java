package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;

/**
 * Manages storage of BookmarkManager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BookmarkManagerStorage bookmarkManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(BookmarkManagerStorage bookmarkManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.bookmarkManagerStorage = bookmarkManagerStorage;
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


    // ================ BookmarkManager methods ==============================

    @Override
    public Path getBookmarkManagerFilePath() {
        return bookmarkManagerStorage.getBookmarkManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyBookmarkManager> readBookmarkManager() throws DataConversionException, IOException {
        return readBookmarkManager(bookmarkManagerStorage.getBookmarkManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyBookmarkManager> readBookmarkManager(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bookmarkManagerStorage.readBookmarkManager(filePath);
    }

    @Override
    public void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager) throws IOException {
        saveBookmarkManager(bookmarkManager, bookmarkManagerStorage.getBookmarkManagerFilePath());
    }

    @Override
    public void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bookmarkManagerStorage.saveBookmarkManager(bookmarkManager, filePath);
    }

}
