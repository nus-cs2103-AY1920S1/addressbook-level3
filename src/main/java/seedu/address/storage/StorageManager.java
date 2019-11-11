package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.exchangedata.ExchangeData;

/**
 * Manages storage of ExpenseList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExpenseListStorage expenseListStorage;
    private ExchangeDataStorage exchangeDataStorage;
    private BudgetListStorage budgetListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ExpenseListStorage expenseListStorage, BudgetListStorage budgetListStorage,
                          ExchangeDataStorage exchangeDataStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.expenseListStorage = expenseListStorage;
        this.exchangeDataStorage = exchangeDataStorage;
        this.budgetListStorage = budgetListStorage;
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


    // ================ ExpenseList methods ==============================

    @Override
    public Path getExpenseListFilePath() {
        return expenseListStorage.getExpenseListFilePath();
    }

    @Override
    public Path getExchangeDataFilePath() {
        return exchangeDataStorage.getExchangeDataFilePath();
    }

    @Override
    public Optional<ExchangeData> readExchangeData() throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: ");
        return exchangeDataStorage.readExchangeData();
    }

    @Override
    public Optional<ExchangeData> readExchangeData(Path filePath) throws DataConversionException, IOException {
        return exchangeDataStorage.readExchangeData(filePath);
    }

    @Override
    public Optional<ReadOnlyExpenseList> readExpenseList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expenseListStorage.readExpenseList(filePath);
    }

    @Override
    public Optional<ReadOnlyExpenseList> readExpenseList() throws DataConversionException, IOException {
        return readExpenseList(expenseListStorage.getExpenseListFilePath());
    }

    @Override
    public void saveExpenseList(ReadOnlyExpenseList expenseList) throws IOException {
        saveExpenseList(expenseList, expenseListStorage.getExpenseListFilePath());
    }

    @Override
    public void saveExpenseList(ReadOnlyExpenseList expenseList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expenseListStorage.saveExpenseList(expenseList, filePath);
    }

    // ================ BudgetList methods ==============================

    @Override
    public Path getBudgetListFilePath() {
        return budgetListStorage.getBudgetListFilePath();
    }

    @Override
    public Optional<ReadOnlyBudgetList> readBudgetList() throws DataConversionException, IOException {
        return readBudgetList(budgetListStorage.getBudgetListFilePath());
    }

    @Override
    public Optional<ReadOnlyBudgetList> readBudgetList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return budgetListStorage.readBudgetList(filePath);
    }

    @Override
    public void saveBudgetList(ReadOnlyBudgetList budgetList) throws IOException {
        saveBudgetList(budgetList, budgetListStorage.getBudgetListFilePath());
    }

    @Override
    public void saveBudgetList(ReadOnlyBudgetList budgetList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        budgetListStorage.saveBudgetList(budgetList, filePath);
    }

}
