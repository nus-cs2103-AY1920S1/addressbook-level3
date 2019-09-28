package thrift.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' THRIFT file path.
     */
    Path getThriftFilePath();

    /**
     * Sets the user prefs' THRIFT file path.
     */
    void setThriftFilePath(Path thriftFilePath);

    /**
     * Replaces THRIFT data with the data in {@code thrift}.
     */
    void setThrift(ReadOnlyThrift thrift);

    /** Returns THRIFT */
    ReadOnlyThrift getThrift();

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the transactions list.
     */
    boolean hasTransaction(Transaction transaction);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the transactions list.
     */
    void deleteTransaction(Transaction transaction);

    /**
     * Adds the given expense.
     */
    void addExpense(Expense expense);

    /**
     * Adds the given income.
     */
    void addIncome(Income income);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the transactions list.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /** Returns an unmodifiable view of the filtered transaction list */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);
}
