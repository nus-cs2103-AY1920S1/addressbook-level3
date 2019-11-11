package seedu.address.storage.finance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.ReadOnlyUserPrefs;
import seedu.address.model.finance.UserPrefs;

/**
 * Manages storage of FinanceLog data in local storage.
 */
public class FinanceStorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(FinanceStorageManager.class);
    private FinanceStorage financeLogStorage;
    private UserPrefsStorage userPrefsStorage;


    public FinanceStorageManager(FinanceStorage financeLogStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.financeLogStorage = financeLogStorage;
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


    // ================ FinanceLog methods ==============================

    @Override
    public Path getFinanceLogFilePath() {
        return financeLogStorage.getFinanceLogFilePath();
    }

    @Override
    public Optional<ReadOnlyFinanceLog> readFinanceLog() throws DataConversionException, IOException {
        return readFinanceLog(financeLogStorage.getFinanceLogFilePath());
    }

    @Override
    public Optional<ReadOnlyFinanceLog> readFinanceLog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return financeLogStorage.readFinanceLog(filePath);
    }

    @Override
    public void saveFinanceLog(ReadOnlyFinanceLog financeLog) throws IOException {
        saveFinanceLog(financeLog, financeLogStorage.getFinanceLogFilePath());
    }

    @Override
    public void saveFinanceLog(ReadOnlyFinanceLog financeLog, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financeLogStorage.saveFinanceLog(financeLog, filePath);
    }

}
