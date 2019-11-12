package seedu.eatme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.ReadOnlyUserPrefs;
import seedu.eatme.model.UserPrefs;

/**
 * Manages storage of EatMe data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EateryListStorage eateryListStorage;
    private UserPrefsStorage userPrefsStorage;
    private FeedListStorage feedListStorage;

    public StorageManager(EateryListStorage eateryListStorage, FeedListStorage feedListStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.eateryListStorage = eateryListStorage;
        this.feedListStorage = feedListStorage;
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


    // ================ EateryList methods ==============================

    @Override
    public Path getEateryListFilePath() {
        return eateryListStorage.getEateryListFilePath();
    }

    @Override
    public Optional<ReadOnlyEateryList> readEateryList() throws DataConversionException, IOException {
        return readEateryList(eateryListStorage.getEateryListFilePath());
    }

    @Override
    public Optional<ReadOnlyEateryList> readEateryList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eateryListStorage.readEateryList(filePath);
    }

    @Override
    public void saveEateryList(ReadOnlyEateryList eateryList) throws IOException {
        saveEateryList(eateryList, eateryListStorage.getEateryListFilePath());
    }

    @Override
    public void saveEateryList(ReadOnlyEateryList eateryList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eateryListStorage.saveEateryList(eateryList, filePath);
    }

    // ================ FeedList methods ==============================

    @Override
    public Path getFeedListFilePath() {
        return feedListStorage.getFeedListFilePath();
    }

    @Override
    public Optional<ReadOnlyFeedList> readFeedList() throws DataConversionException, IOException {
        return readFeedList(feedListStorage.getFeedListFilePath());
    }

    @Override
    public Optional<ReadOnlyFeedList> readFeedList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return feedListStorage.readFeedList(filePath);
    }

    @Override
    public void saveFeedList(ReadOnlyFeedList feedList) throws IOException {
        saveFeedList(feedList, feedListStorage.getFeedListFilePath());
    }

    @Override
    public void saveFeedList(ReadOnlyFeedList feedList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        feedListStorage.saveFeedList(feedList, filePath);
    }

}
