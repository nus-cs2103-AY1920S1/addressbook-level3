package thrift.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.commons.core.index.Index;
import thrift.logic.commands.Undoable;
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
     * Adds the given expense to a specified index.
     */
    void addExpense(Expense expense, Index index);

    /**
     * Adds the given income.
     */
    void addIncome(Income income);

    /**
     * Adds the given income to a specified index.
     */
    void addIncome(Income income, Index index);

    /**
     * Replaces the given transaction {@code target} with {@code updatedTransaction}.
     * {@code target} must exist in the transactions list.
     */
    void setTransaction(Transaction target, Transaction updatedTransaction);

    /** Returns an unmodifiable view of the filtered transaction list */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Keeps track of past undoable commands.
     */
    void keepTrackCommands(Undoable command);

    /**
     * Returns the previous undoable command.
     *
     * @return previous undoable command
     */
    Undoable getPreviousUndoableCommand();

    /**
     * Checks if there is any undoable command to undo.
     *
     * @return true if there is undoable command, else false.
     */
    boolean hasUndoableCommand();
}
