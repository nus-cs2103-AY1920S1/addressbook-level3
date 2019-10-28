package thrift.model;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.commons.core.index.Index;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.transaction.Budget;
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
     * Returns an Optional that contains the {@link Index} of the {@code transaction}.
     *
     * @param transaction is the transaction that you are interested in its index in the full transaction list.
     * @return an Optional containing the index of the transaction.
     */
    Optional<Index> getIndexInFullTransactionList(Transaction transaction);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the transactions list.
     */
    void deleteTransaction(Transaction transaction);

    /**
     * Deletes the transaction based on given index.
     */
    void deleteTransaction(Index index);

    /**
     * Deletes the last transaction in the full transaction list.
     *
     * @return deleted transaction.
     */
    Transaction deleteLastTransaction();

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
     * Sets the monthly budget to the specified budget.
     *
     * @param budget is the budget to be replaced.
     * @return replaced budget wrapped in optional.
     */
    Optional<Budget> setBudget(Budget budget);

    /**
     * Resets the monthly budget to null.
     *
     * @param budget is the budget to be removed.
     */
    void resetBudgetForThatMonth(Budget budget);

    /**
     * Replaces the given transaction {@code target} with {@code updatedTransaction}.
     * {@code target} must exist in the transactions list.
     */
    void setTransaction(Transaction target, Transaction updatedTransaction);

    /**
     * Replaces the given transaction {@code actualIndex} with {@code updatedTransaction}.
     * {@code actualIndex} must be a valid {@code Index}.
     */
    void setTransactionWithIndex(Index actualIndex, Transaction updatedTransaction);

    /** Returns the current month and year in MMM yyyy format. */
    String getCurrentMonthYear();

    /**
     * Sets the CurrentMonthYear
     */
    void setCurrentMonthYear(Calendar monthYear);

    /** Returns the current month's budget. */
    double getCurrentMonthBudget();

    /** Returns an unmodifiable view of the filtered transaction list */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Filters the view of the transaction list to only show transactions that occur in the current month. */
    void updateFilteredTransactionListToCurrentMonth();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Updates the balance tracked by the model by summing values from the {@code Transaction} in the filteredList.
     */
    void updateBalanceForCurrentMonth();

    /**
     * Returns the balance held by the model to update the GUI with.
     */
    double getBalance();

    /**
     * Updates the expense tracked by the model by summing values from the {@code Transaction} in the filteredList.
     */
    void updateExpenseForCurrentMonth();

    /**
     * Returns the expense held by the model to update the GUI with.
     */
    double getExpense();

    /**
     * Updates the income tracked by the model by summing values from the {@code Transaction} in the filteredList.
     */
    void updateIncomeForCurrentMonth();

    /**
     * Returns the income held by the model to update the GUI with.
     */
    double getIncome();

    /**
     * Returns if {@code transaction} is currently in {@code FilteredList<Transaction>}.
     */
    boolean isInView(Transaction transaction);

    /**
     * Keeps track of past undoable commands.
     */
    void keepTrackCommands(Undoable command);

    /**
     * Returns the previous undoable command.
     *
     * @return previous undoable command
     * @throws CommandException if there is no command available to undo.
     */
    Undoable getPreviousUndoableCommand() throws CommandException;

    /**
     * Checks if there is any undoable command to undo.
     *
     * @return true if there is undoable command, else false.
     */
    boolean hasUndoableCommand();

    /**
     * Returns the undone command.
     *
     * @return undone command.
     * @throws CommandException if there is no command available to redo.
     */
    Undoable getUndoneCommand() throws CommandException;

    /**
     * Checks if there is any undone command to redo.
     *
     * @return true if there is undone command, else false.
     */
    boolean hasUndoneCommand();
}
