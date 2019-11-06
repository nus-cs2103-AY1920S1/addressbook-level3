package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ProjectCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.UniqueTransactionList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final VersionedUserState versionedUserState;
    private final FilteredList<BankAccountOperation> filteredTransactions;
    private final FilteredList<Budget> filteredBudgets;
    private final FilteredList<LedgerOperation> filteredLedgerOperations;
    private final FilteredList<Projection> filteredProjections;

    /**
     * Initializes a ModelManager with the given bankAccount and userPrefs.
     */
    public ModelManager(ReadOnlyUserState userState, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userState, userPrefs);

        logger.fine("Initializing with bank account" + userState + " and user prefs " + userPrefs);

        this.versionedUserState = new VersionedUserState(userState);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTransactions = new FilteredList<>(this.versionedUserState.getBankAccount().getTransactionHistory());
        filteredBudgets = new FilteredList<>(this.versionedUserState.getBankAccount().getBudgetHistory());
        filteredLedgerOperations = new FilteredList<>(this.versionedUserState.getLedger().getLedgerHistory());
        filteredProjections = new FilteredList<>(this.versionedUserState.getBankAccount().getProjectionHistory());
    }

    public ModelManager() {
        this(new UserState(), new UserPrefs());
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

    //=========== Bank Account =============================================================

    @Override
    public Path getUserStateFilePath() {
        return userPrefs.getUserStateFilePath();
    }

    @Override
    public void setUserStateFilePath(Path userStateFilePath) {
        requireNonNull(userStateFilePath);
        userPrefs.setUserStateFilePath(userStateFilePath);
    }

    @Override
    public void setUserState(ReadOnlyUserState userState) {
        requireNonNull(userState);
        this.versionedUserState.resetData(userState);
    }

    @Override
    public ReadOnlyBankAccount getBankAccount() {
        return versionedUserState.getBankAccount();
    }

    @Override
    public ReadOnlyUserState getUserState() {
        return this.versionedUserState;
    }

    @Override
    public boolean has(BankAccountOperation transaction) {
        requireNonNull(transaction);
        return versionedUserState.getBankAccount().has(transaction);
    }

    @Override
    public boolean has(Budget budget) {
        requireNonNull(budget);
        return versionedUserState.getBankAccount().has(budget);
    }

    @Override
    public boolean has(LedgerOperation ledgerOperation) {
        return versionedUserState.getLedger().has(ledgerOperation);
    }

    @Override
    public boolean has(Projection projection) {
        return versionedUserState.getBankAccount().has(projection);
    }

    @Override
    public void deleteTransaction(BankAccountOperation transaction) {
        versionedUserState.remove(transaction);
    }

    @Override
    public void deleteBudget(Budget budget) {
        versionedUserState.remove(budget);
    }

    @Override
    public void deleteProjection(Projection projectionToDelete) {
        versionedUserState.remove(projectionToDelete);
    }

    @Override
    public void deleteLedger(LedgerOperation ledgerToDelete) {
        versionedUserState.remove(ledgerToDelete);
    }

    @Override
    public void set(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        requireAllNonNull(transactionTarget, transactionEdit);

        versionedUserState.set(transactionTarget, transactionEdit);
    }

    @Override
    public void set(LedgerOperation ledgerTarget, LedgerOperation ledgerEdit) {
        requireAllNonNull(ledgerTarget, ledgerEdit);

        versionedUserState.set(ledgerTarget, ledgerEdit);
    }

    @Override
    public void set(Budget budgetTarget, Budget budgetEdit) {
        requireAllNonNull(budgetTarget, budgetEdit);

        versionedUserState.set(budgetTarget, budgetEdit);
    }

    @Override
    public void add(Budget budget) {
        versionedUserState.add(budget);
    }

    @Override
    public void add(BankAccountOperation operation) {
        versionedUserState.add(operation);
    }

    @Override
    public void add(LedgerOperation operation) {
        versionedUserState.add(operation);
    }

    @Override
    public void add(Projection projection) {
        versionedUserState.add(projection);
    }

    @Override
    public ObservableList<BankAccountOperation> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudgets;
    }

    @Override
    public ObservableList<LedgerOperation> getFilteredLedgerOperationsList() {
        return filteredLedgerOperations;
    }

    @Override
    public ObservableList<Projection> getFilteredProjectionsList() {
        return filteredProjections;
    }

    @Override
    public boolean canUndoUserState() {
        return versionedUserState.canUndo();
    }

    @Override
    public void undoUserState() {
        versionedUserState.undo();
    }

    @Override
    public boolean canRedoUserState() {
        return versionedUserState.canRedo();
    }

    @Override
    public void redoUserState() {
        versionedUserState.redo();
    }

    @Override
    public void commitUserState() {
        versionedUserState.commit();
    }

    @Override
    public void setTransactions(List<BankAccountOperation> transactionHistory) {
        versionedUserState.set(transactionHistory);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void updateFilteredLedgerList(Predicate<LedgerOperation> predicate) {
        requireNonNull(predicate);
        filteredLedgerOperations.setPredicate(predicate);
    }

    @Override
    public void updateProjectionsAfterDelete(BankAccountOperation deleted) throws CommandException {
        this.getFilteredProjectionsList().forEach(x -> {
            if (deleted.getCategories().isEmpty() && x.getCategory() == null) {
                this.deleteProjection(x);
                if (x.getBudgets().isPresent()) {
                    this.add(new Projection(this.getFilteredTransactionList()
                            .filtered(t -> t.getCategories().isEmpty()), x.getDate(), x.getBudgets().get()));
                } else {
                    this.add(new Projection(this.getFilteredTransactionList()
                            .filtered(t -> t.getCategories().isEmpty()), x.getDate()));
                }
            } else {
                boolean sameCategory = deleted.getCategories().stream().anyMatch(c -> {
                    if (x.getCategory() != null) {
                        return c.equals(x.getCategory());
                    }
                    return false;
                });
                if (sameCategory) {
                    UniqueTransactionList txns = new UniqueTransactionList();
                    txns.setTransactions(x.getTransactionHistory());
                    txns.remove(deleted);
                    this.add(new Projection(txns.asUnmodifiableObservableList(),
                            x.getDate(), x.getBudgets().get(), x.getCategory()));
                }
            }
        });
    }

    // TODO: Fix general to mean all
    @Override
    public void updateProjectionsAfterAdd(BankAccountOperation added) throws CommandException {
        this.getFilteredProjectionsList().forEach(x -> {
            if (added.isGeneral() && x.getCategory() == null) {
                this.deleteProjection(x);
                ObservableList<BankAccountOperation> newTransactions =
                        this.getFilteredTransactionList().filtered(t -> t.getCategories().isEmpty());
                if (newTransactions.size() >= ProjectCommand.REQUIRED_MINIMUM_TRANSACTIONS
                        && x.getBudgets().isPresent()) {
                    this.add(new Projection(newTransactions, x.getDate(), x.getBudgets().get()));
                } else if (newTransactions.size() >= ProjectCommand.REQUIRED_MINIMUM_TRANSACTIONS) {
                    this.add(new Projection(newTransactions, x.getDate()));
                }
            } else {
                boolean sameCategory = added.getCategories().stream().anyMatch(c -> {
                    if (x.getCategory() != null) {
                        return c.equals(x.getCategory());
                    }
                    return false;
                });
                if (sameCategory) {
                    UniqueTransactionList txns = new UniqueTransactionList();
                    txns.setTransactions(x.getTransactionHistory());
                    txns.add(added);
                    this.add(new Projection(txns.asUnmodifiableObservableList(),
                            x.getDate(), x.getBudgets().get(), x.getCategory()));
                }
            }
        });
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
        return versionedUserState.equals(other.versionedUserState)
            && userPrefs.equals(other.userPrefs)
            && filteredTransactions.equals(other.filteredTransactions)
            && filteredBudgets.equals(other.filteredBudgets);
    }

}
