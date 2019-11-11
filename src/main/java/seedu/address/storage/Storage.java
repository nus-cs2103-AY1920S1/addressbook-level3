package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.exchangedata.ExchangeData;

/**
 * API of the Storage component
 */
public interface Storage extends ExpenseListStorage, BudgetListStorage, ExchangeDataStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getExpenseListFilePath();

    @Override
    Optional<ReadOnlyExpenseList> readExpenseList() throws DataConversionException, IOException;

    @Override
    Optional<ExchangeData> readExchangeData() throws DataConversionException, IOException;

    void saveExpenseList(ReadOnlyExpenseList expenseList) throws IOException;

    @Override
    Path getBudgetListFilePath();

    @Override
    Optional<ReadOnlyBudgetList> readBudgetList() throws DataConversionException, IOException;

    @Override
    void saveBudgetList(ReadOnlyBudgetList budgetList) throws IOException;
}
