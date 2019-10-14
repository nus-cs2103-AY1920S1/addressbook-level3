package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of BankAccount data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BankAccountStorage bankAccountStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(BankAccountStorage bankAccountStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.bankAccountStorage = bankAccountStorage;
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
    public Optional<ReadOnlyBankAccount> readBankAccount() throws DataConversionException, IOException {
        return readBankAccount(bankAccountStorage.getBankAccountFilePath());
    }

    @Override
    public Optional<ReadOnlyBankAccount> readBankAccount(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bankAccountStorage.readBankAccount(filePath);
    }

    @Override
    public void saveBankAccount(ReadOnlyBankAccount bankAccount) throws IOException {
        saveBankAccount(bankAccount, bankAccountStorage.getBankAccountFilePath());
    }

    @Override
    public void saveBankAccount(ReadOnlyBankAccount bankAccount, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bankAccountStorage.saveBankAccount(bankAccount, filePath);
    }

}
