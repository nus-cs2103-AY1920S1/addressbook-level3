package budgetbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.ReadOnlyUserPrefs;
import budgetbuddy.model.UserPrefs;
import budgetbuddy.storage.loans.LoansStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LoansStorage loansStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(LoansStorage loansStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.loansStorage = loansStorage;
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

    // ================ Loan Storage methods ==============================

    @Override
    public Path getLoansFilePath() {
        return loansStorage.getLoansFilePath();
    }

    @Override
    public Optional<LoansManager> readLoans() throws DataConversionException, IOException {
        return readLoans(getLoansFilePath());
    }

    @Override
    public Optional<LoansManager> readLoans(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return loansStorage.readLoans(filePath);
    }

    @Override
    public void saveLoans(LoansManager loansManager) throws IOException {
        saveLoans(loansManager, loansStorage.getLoansFilePath());
    }

    @Override
    public void saveLoans(LoansManager loansManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        loansStorage.saveLoans(loansManager, filePath);
    }
}
