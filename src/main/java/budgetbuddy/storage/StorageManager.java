package budgetbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.ReadOnlyUserPrefs;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.UserPrefs;
import budgetbuddy.storage.accounts.AccountsStorage;
import budgetbuddy.storage.loans.LoansStorage;
import budgetbuddy.storage.rules.RuleStorage;
import budgetbuddy.storage.scripts.ScriptsStorage;
import budgetbuddy.storage.scripts.exceptions.ScriptsStorageException;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AccountsStorage accountsStorage;
    private LoansStorage loansStorage;
    private RuleStorage ruleStorage;
    private ScriptsStorage scriptsStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AccountsStorage accountsStorage, LoansStorage loansStorage, RuleStorage ruleStorage,
                          ScriptsStorage scriptsStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.loansStorage = loansStorage;
        this.ruleStorage = ruleStorage;
        this.scriptsStorage = scriptsStorage;
        this.accountsStorage = accountsStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    @Override
    public void save(Model model) throws IOException {
        saveAccounts(model.getAccountsManager());
        saveLoans(model.getLoansManager());
        saveRules(model.getRuleManager());
        saveScripts(model.getScriptLibrary());
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

    // ================ Rule Storage methods ==============================

    @Override
    public Path getRuleFilePath() {
        return ruleStorage.getRuleFilePath();
    }

    @Override
    public Optional<RuleManager> readRules() throws DataConversionException, IOException {
        return readRules(getRuleFilePath());
    }

    @Override
    public Optional<RuleManager> readRules(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return ruleStorage.readRules(filePath);
    }

    @Override
    public void saveRules(RuleManager ruleManager) throws IOException {
        saveRules(ruleManager, ruleStorage.getRuleFilePath());
    }

    @Override
    public void saveRules(RuleManager ruleManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        ruleStorage.saveRules(ruleManager, filePath);
    }

    @Override
    public Path getScriptsPath() {
        return scriptsStorage.getScriptsPath();
    }

    @Override
    public ScriptLibrary readScripts(Path scriptsPath) throws IOException, ScriptsStorageException {
        return scriptsStorage.readScripts(scriptsPath);
    }

    @Override
    public void saveScripts(ScriptLibrary scripts, Path scriptsPath) throws IOException {
        scriptsStorage.saveScripts(scripts, scriptsPath);
    }

    // ================ Account Storage methods ==============================

    @Override
    public Path getAccountsFilePath() {
        return accountsStorage.getAccountsFilePath();
    }

    @Override
    public Optional<AccountsManager> readAccounts() throws DataConversionException, IOException {
        return readAccounts(getAccountsFilePath());
    }

    @Override
    public Optional<AccountsManager> readAccounts(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return accountsStorage.readAccounts(filePath);
    }

    @Override
    public void saveAccounts(AccountsManager accountsManager) throws IOException {
        saveAccounts(accountsManager, accountsStorage.getAccountsFilePath());
    }

    @Override
    public void saveAccounts(AccountsManager accountsManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        accountsStorage.saveAccounts(accountsManager, filePath);
    }
}
