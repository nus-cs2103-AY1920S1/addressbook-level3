package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.ui.tab.Tab;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<BankAccountOperation> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;
    Predicate<LedgerOperation> PREDICATE_SHOW_ALL_LEDGER_OPERATIONS = unused -> true;

    String LEDGER_TYPE = "l";
    String BUDGET_TYPE = "b";
    String TRANSACTION_TYPE = "t";
    String PROJECTION_TYPE = "p";

    //=========== UserPrefs ==================================================================================
    Tab getCurrentTab();

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
     * Returns the user prefs' bank account file path.
     */
    Path getUserStateFilePath();

    /**
     * Sets the user prefs' bank account file path.
     */
    void setUserStateFilePath(Path bankAccountFilePath);

    /**
     * Replaces bank account data with the data in {@code bankAccount}.
     */
    void setUserState(ReadOnlyUserState bankAccount);

    /**
     * Returns the BankAccount
     */
    ReadOnlyBankAccount getBankAccount();

    /**
     * Returns true if the model has previous bank account states to restore.
     */
    boolean canUndoUserState();

    /**
     * Restores the model's bank account to its previous state.
     */
    void undoUserState();

    /**
     * Returns true if the model has undone bank account states to restore.
     */
    boolean canRedoUserState();

    /**
     * Restores the model's bank account to its previously undone state.
     */
    void redoUserState();

    /**
     * Saves the current bank account state for undo/redo.
     */
    void commitUserState();

    /**
     * Replaces the existing transaction history in the bank account
     * with {@code transactionHistory}.
     *
     * @param transactionHistory
     */
    void setTransactions(List<BankAccountOperation> transactionHistory);

    ReadOnlyUserState getUserState();

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the bank account.
     *
     * @param transaction
     */
    boolean has(BankAccountOperation transaction);

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the bank account.
     *
     * @param budget
     */
    boolean has(Budget budget);

    /**
     * Returns true if a ledgerOperation with the same identity as {@code ledgerOperation} exists in the ledger.
     *
     * @param ledgerOperation
     */
    boolean has(LedgerOperation ledgerOperation);

    /**
     * Returns true if a projection with the same date as {@code projection} exists in the bank account.
     */
    boolean has(Projection projection);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the bank account.
     *
     * @param transaction
     */
    void delete(BankAccountOperation transaction);

    /**
     * Deletes the given budget.
     * The budget must exist in the bank account.
     */
    void delete(Budget budgetToDelete);

    /**
     * Deletes the given projection.
     * The projection must exist in the bank account.
     */
    void delete(Projection projectionToDelete);

    /**
     * Deletes the given ledger.
     * The ledger must exist in the bank account.
     */
    void delete(LedgerOperation ledgerToDelete);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the bank account.
     * The transaction identity of {@code editedTransaction} must not be
     * the same as another existing transaction in the bank account.
     */
    void set(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit);

    /**
     * Replaces the given ledger operation {@code ledgerTarget} with {@code ledgerEdit}.
     * {@code target} must exist in the bank account.
     * The ledger operation identity of {@code ledgerEdit} must not be
     * the same as another existing ledger operation in the Ledger.
     */
    void set(LedgerOperation ledgerTarget, LedgerOperation ledgerEdit);

    /**
     * Replaces the given budget {@code budget} with {@code editedBudget}.
     * {@code target} must exist in the bank account.
     * The budget identity of {@code editedBudget} must not be
     * the same as another existing budget in the bank account.
     */
    void set(Budget budgetTarget, Budget budgetEdit);

    /**
     * Adds the given transaction.
     * {@code transaction} must not already exist in the bank account.
     *
     * @param operation
     */
    void add(BankAccountOperation operation);

    /**
     * Adds the given ledgerOperation.
     * {@code ledgerOperation} must not already exist in the bank account.
     */
    void add(LedgerOperation operation);

    /**
     * Adds the given budget.
     * {@code budget} must not already exist in the bank account.
     */
    void add(Budget budget);

    /**
     * Adds the given projection.
     * {@code projection} must not already exist in the bank account.
     */
    void add(Projection projection);

    /**
     * Returns an unmodifiable view of the non-filtered transaction list.
     */
    List<BankAccountOperation> getTransactionList();

    /**
     * Returns an unmodifiable view of the filtered transaction list.
     */
    ObservableList<BankAccountOperation> getFilteredTransactionList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate);

    void updateFilteredLedgerList(Predicate<LedgerOperation> predicate);

    void updateProjectionsAfterAdd(BankAccountOperation added);

    void updateProjectionsAfterAdd(Budget budget);

    void updateProjectionsAfterDelete(Budget budget);

    void updateProjectionsAfterDelete(BankAccountOperation deleted);

    void updateProjectionsAfterUpdate(BankAccountOperation toUpdate, BankAccountOperation updated);

    /**
     * Returns an unmodifiable view of the filtered budget list
     */
    ObservableList<Budget> getFilteredBudgetList();


    ObservableList<LedgerOperation> getFilteredLedgerOperationsList();

    ObservableList<Projection> getFilteredProjectionsList();

    ObservableList<Person> getPeopleInLedger();

    void setTab(Tab tab);
}
