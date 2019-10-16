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
import seedu.weme.statistics.StatsEngine;

/**
 * Manages storage of MemeBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MemeBookStorage memeBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private StatsDataStorage statsDataStorage;


    public StorageManager(MemeBookStorage memeBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          StatsDataStorage statsDataStorage) {
        super();
        this.memeBookStorage = memeBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.statsDataStorage = statsDataStorage;
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

    // ================ Statistics methods ==============================

    @Override
    public Path getStatsDataPath() {
        return statsDataStorage.getStatsDataPath();
    }

    @Override
    public Optional<StatsEngine> readStatsData() throws DataConversionException, IOException {
        return readStatsData(getStatsDataPath());
    }

    @Override
    public Optional<StatsEngine> readStatsData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return statsDataStorage.readStatsData(filePath);
    }

    @Override
    public void saveStatsData(StatsEngine statsEngine) throws IOException {
        saveStatsData(statsEngine, statsDataStorage.getStatsDataPath());
    }

    @Override
    public void saveStatsData(StatsEngine statsEngine, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        statsDataStorage.saveStatsData(statsEngine, filePath);
    }
}
