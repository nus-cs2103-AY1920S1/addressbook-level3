package seedu.ezwatchlist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.commons.exceptions.DataConversionException;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.ReadOnlyUserPrefs;
import seedu.ezwatchlist.model.UserPrefs;

/**
 * Manages storage of WatchList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WatchListStorage watchListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(WatchListStorage watchListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.watchListStorage = watchListStorage;
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


    // ================ WatchList methods ==============================

    @Override
    public Path getWatchListFilePath() {
        return watchListStorage.getWatchListFilePath();
    }

    @Override
    public Optional<ReadOnlyWatchList> readWatchList() throws DataConversionException, IOException {
        return readWatchList(watchListStorage.getWatchListFilePath());
    }

    @Override
    public Optional<ReadOnlyWatchList> readWatchList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return watchListStorage.readWatchList(filePath);
    }

    @Override
    public void saveWatchList(ReadOnlyWatchList watchList) throws IOException {
        saveWatchList(watchList, watchListStorage.getWatchListFilePath());
    }

    @Override
    public void saveWatchList(ReadOnlyWatchList watchList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        watchListStorage.saveWatchList(watchList, filePath);
    }

}
