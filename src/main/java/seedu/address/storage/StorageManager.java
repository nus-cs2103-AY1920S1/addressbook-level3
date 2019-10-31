package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of BankAccount data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BankAccountStorage bankAccountStorage;
    private UserStateStorage userStateStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(BankAccountStorage bankAccountStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.bankAccountStorage = bankAccountStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(UserStateStorage userStateStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.userStateStorage = userStateStorage;
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


    // ================ BankAccount methods ==============================

    @Override
    public Path getBankAccountFilePath() {
        return bankAccountStorage.getBankAccountFilePath();
    }

    @Override
    public Optional<ReadOnlyUserState> readAccount() throws DataConversionException, IOException {
        return readAccount(userStateStorage.getUserStateFilePath());
    }

    @Override
    public Optional<ReadOnlyUserState> readAccount(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userStateStorage.readUserState(filePath);
    }

    public void saveAccount(ReadOnlyUserState bankAccount) throws IOException {
        saveAccount(bankAccount, bankAccountStorage.getBankAccountFilePath());
    }

    public void saveAccount(ReadOnlyUserState bankAccount, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bankAccountStorage.saveAccount(bankAccount, filePath);
    }

}
