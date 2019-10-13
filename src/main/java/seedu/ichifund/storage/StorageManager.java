package seedu.ichifund.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.commons.exceptions.DataConversionException;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.ReadOnlyUserPrefs;
import seedu.ichifund.model.UserPrefs;

/**
 * Manages storage of FundBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FundBookStorage fundBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FundBookStorage fundBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.fundBookStorage = fundBookStorage;
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


    // ================ FundBook methods ==============================

    @Override
    public Path getFundBookFilePath() {
        return fundBookStorage.getFundBookFilePath();
    }

    @Override
    public Optional<ReadOnlyFundBook> readFundBook() throws DataConversionException, IOException {
        return readFundBook(fundBookStorage.getFundBookFilePath());
    }

    @Override
    public Optional<ReadOnlyFundBook> readFundBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return fundBookStorage.readFundBook(filePath);
    }

    @Override
    public void saveFundBook(ReadOnlyFundBook fundBook) throws IOException {
        saveFundBook(fundBook, fundBookStorage.getFundBookFilePath());
    }

    @Override
    public void saveFundBook(ReadOnlyFundBook fundBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        fundBookStorage.saveFundBook(fundBook, filePath);
    }

}
