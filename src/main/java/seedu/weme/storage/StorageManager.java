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
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.LikeDataImpl;

/**
 * Manages storage of MemeBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MemeBookStorage memeBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private LikeDataStorage likeDataStorage;


    public StorageManager(MemeBookStorage memeBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          LikeDataStorage likeDataStorage) {
        super();
        this.memeBookStorage = memeBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.likeDataStorage = likeDataStorage;
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

    // ================ LikeData methods ==============================

    public Path getLikeDataFilePath() {
        return likeDataStorage.getLikeDataFilePath();
    }

    public Optional<LikeData> readLikeData() throws DataConversionException, IOException {
        return readLikeData(getLikeDataFilePath());
    }

    public Optional<LikeData> readLikeData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return likeDataStorage.readLikeData(filePath);
    }

    public void saveLikeData(LikeDataImpl likeData) throws IOException {
        saveLikeData(likeData, likeDataStorage.getLikeDataFilePath());
    }

    public void saveLikeData(LikeDataImpl likeData, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        likeDataStorage.saveLikeData(likeData, filePath);
    }
}
