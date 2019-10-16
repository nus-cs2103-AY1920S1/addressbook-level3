package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expense.Expense;

/**
 * Represents the in-memory model of the expense list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ExpenseList expenseList;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpenses;

    /**
     * Initializes a ModelManager with the given expenseList and userPrefs.
     */
    public ModelManager(ReadOnlyExpenseList expenseList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseList, userPrefs);

        logger.fine("Initializing with expense list: " + expenseList + " and user prefs " + userPrefs);

        this.expenseList = new ExpenseList(expenseList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenses = new FilteredList<>(this.expenseList.getExpenseList());
    }

    public ModelManager() {
        this(new ExpenseList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getExpenseListFilePath() {
        return userPrefs.getExpenseListFilePath();
    }

    @Override
    public void setExpenseListFilePath(Path expenseListFilePath) {
        requireNonNull(expenseListFilePath);
        userPrefs.setExpenseListFilePath(expenseListFilePath);
    }

    //=========== ExpenseList ================================================================================

    @Override
    public void setExpenseList(ReadOnlyExpenseList expenseList) {
        this.expenseList.resetData(expenseList);
    }

    @Override
    public ReadOnlyExpenseList getExpenseList() {
        return expenseList;
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenseList.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        expenseList.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        expenseList.addExpense(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        expenseList.setExpense(target, editedExpense);
    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return expenseList.equals(other.expenseList)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenses.equals(other.filteredExpenses);
    }

}
