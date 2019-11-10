package seedu.ichifund.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Repeater> PREDICATE_SHOW_ALL_REPEATERS = unused -> true;
    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

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
     * Returns the user prefs' fund book file path.
     */
    Path getFundBookFilePath();

    /**
     * Sets the user prefs' fund book file path.
     */
    void setFundBookFilePath(Path fundBookFilePath);

    /**
     * Replaces fund book data with the data in {@code fundBook}.
     */
    void setFundBook(ReadOnlyFundBook fundBook);

    /** Returns the FundBook */
    ReadOnlyFundBook getFundBook();

    /**
     * Gets the current repeeater unique id.
     */
    RepeaterUniqueId getCurrentRepeaterUniqueId();

    /**
     * Sets current repeater unique id to {@code uniqueId}.
     */
    void setCurrentRepeaterUniqueId(RepeaterUniqueId uniqueId);

    /**
     * Adds the given transaction. Changes the view of the list to show the new transaction.
     */
    void addTransaction(Transaction transaction);

    /**
     * Deletes the given transaction.
     * The repeater must exist in the fund book.
     */
    void deleteTransaction(Transaction target);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the fund book.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /** Returns an unmodifiable view of the filtered transaction list. */
    ObservableList<Transaction> getFilteredTransactionList();

    TransactionContext getTransactionContext();

    /** Sets the transactionContext and updates the filtered transaction list accordingly */
    void setTransactionContext(TransactionContext transactionContext);

    /** Updates transactionContext to show a transaction.  */
    void updateTransactionContext(Transaction transaction);

    /**
     * Returns an unmodifiable view of the transactions associated with {@code repeaterUniqueId}.
     */
    ObservableList<Transaction> getAssociatedTransactions(RepeaterUniqueId repeaterUniqueId);

    /**
     * Returns true if a repeater with the same identity as {@code repeater} exists in the fund book.
     */
    boolean hasRepeater(Repeater repeater);

    /**
     * Deletes the given repeater.
     * The repeater must exist in the fund book.
     */
    void deleteRepeater(Repeater target);

    /**
     * Deletes all transactions associated with given repeater unique id.
     */
    void deleteRepeaterTransactions(RepeaterUniqueId repeaterUniqueId);

    /**
     * Adds the given repeater.
     * {@code repeater} must not already exist in the fund book.
     */
    void addRepeater(Repeater repeater);

    /**
     * Creates all transactions associated with given repeater.
     */
    void createRepeaterTransactions(Repeater repeater);

    /**
     * Replaces the given repeater {@code target} with {@code editedRepeater}.
     * {@code target} must exist in the fund book.
     * The repeater identity of {@code editedRepeater} must not be the same as another existing repeater in the fund
     * book.
     */
    void setRepeater(Repeater target, Repeater editedRepeater);

    /** Returns an unmodifiable view of the filtered repeater list */
    ObservableList<Repeater> getFilteredRepeaterList();

    /**
     * Updates the filter of the filtered repeater list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRepeaterList(Predicate<Repeater> predicate);

    /// Loans

    boolean hasLoan(Loan loan);

    void addLoan(Loan loan);

    LoanId getCurrentLoanId();

    public void setCurrentLoanId(LoanId loanId);

    ////////


    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the fund book.
     */
    boolean hasBudget(Budget budget);

    /**
     * Deletes the given budget.
     * The budget must exist in the fund book.
     */
    void deleteBudget(Budget target);

    /**
     * Adds the given budget.
     * {@code budget} must not already exist in the fund book.
     */
    void addBudget(Budget budget);

    /**
     * Replaces the given budget {@code target} with {@code editedBudget}.
     * {@code target} must exist in the fund book.
     * The budget identity of {@code editedBudget} must not be the same as another existing budget in the fund book.
     */
    void setBudget(Budget target, Budget editedBudget);

    /** Returns an unmodifiable view of the filtered budget list */
    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Updates the filter of the filtered budget list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBudgetList(Predicate<Budget> predicate);

    ObservableValue<TransactionContext> getTransactionContextProperty();

    /** Returns the current analytics view */
    ObservableList<Data> getDataList();

    /** Updates the current analytics view */
    void updateDataList(List<Data> datas);

}
