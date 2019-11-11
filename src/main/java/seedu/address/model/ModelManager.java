package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ProjectCommand;
import seedu.address.model.person.Person;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.UniqueBudgetList;
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
    public void delete(BankAccountOperation transaction) {
        versionedUserState.remove(transaction);
    }

    @Override
    public void delete(Budget budget) {
        versionedUserState.remove(budget);
    }

    @Override
    public void delete(Projection projectionToDelete) {
        versionedUserState.remove(projectionToDelete);
    }

    @Override
    public void delete(LedgerOperation ledgerToDelete) {
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
    public List<BankAccountOperation> getTransactionList() {
        Predicate<? super BankAccountOperation> pred = this.filteredTransactions.getPredicate();
        this.filteredTransactions.setPredicate(PREDICATE_SHOW_ALL_TRANSACTIONS);
        List<BankAccountOperation> transactionList = new ArrayList<>(this.filteredTransactions);
        this.filteredTransactions.setPredicate(pred);
        return transactionList;
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
    public ObservableList<Person> getPeopleInLedger() {
        return versionedUserState.getLedger().getPeople();
    }

    @Override
    public boolean canUndoUserState() {
        return versionedUserState.canUndo();
    }

    @Override
    public void undoUserState() {
        versionedUserState.undo();
        updateAllPredicates();
    }

    @Override
    public boolean canRedoUserState() {
        return versionedUserState.canRedo();
    }

    @Override
    public void redoUserState() {
        versionedUserState.redo();
        updateAllPredicates();
    }

    @Override
    public void commitUserState() {
        Predicate<? super BankAccountOperation> transPred = this.filteredTransactions.getPredicate();
        Predicate<? super Budget> budgetPred = this.filteredBudgets.getPredicate();
        Predicate<? super LedgerOperation> ledgerPred = this.filteredLedgerOperations.getPredicate();
        Predicate<? super Projection> projectionPred = this.filteredProjections.getPredicate();
        versionedUserState.commit(transPred, budgetPred, ledgerPred, projectionPred);
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
    public void updateProjectionsAfterAdd(BankAccountOperation added) {
        this.getFilteredProjectionsList().forEach(x -> {
            if (x.isGeneral()) {
                this.delete(x);
                UniqueTransactionList newTransactions = new UniqueTransactionList();
                newTransactions.setTransactions(x.getTransactionHistory());
                newTransactions.add(added);
                this.add(new Projection(newTransactions.asUnmodifiableObservableList(), x.getDate(), x.getBudgets()));
            } else {
                boolean sameCategory = added.getCategories().stream().anyMatch(c -> {
                    if (x.getCategory() != null) {
                        return c.equals(x.getCategory());
                    }
                    return false;
                });
                if (sameCategory) {
                    this.delete(x);
                    UniqueTransactionList newTransactions = new UniqueTransactionList();
                    newTransactions.setTransactions(x.getTransactionHistory());
                    newTransactions.add(added);
                    this.add(new Projection(newTransactions.asUnmodifiableObservableList(),
                        x.getDate(), x.getBudgets(), x.getCategory()));
                }
            }
        });
    }

    @Override
    public void updateProjectionsAfterAdd(Budget added) {
        this.getFilteredProjectionsList().forEach(x -> {
            if (added.isGeneral() && x.isGeneral()) {
                this.delete(x);
                UniqueBudgetList newBudgets = new UniqueBudgetList();
                newBudgets.setBudgets(x.getBudgets());
                newBudgets.add(added);
                this.add(new Projection(this.getFilteredTransactionList(), x.getDate(),
                    newBudgets.asUnmodifiableObservableList()));
            } else {
                boolean sameCategory = added.getCategories().stream().anyMatch(c -> {
                    if (x.getCategory() != null) {
                        return c.equals(x.getCategory());
                    }
                    return false;
                });
                if (sameCategory) {
                    this.delete(x);
                    UniqueBudgetList newBudgets = new UniqueBudgetList();
                    newBudgets.setBudgets(x.getBudgets());
                    newBudgets.add(added);
                    this.add(new Projection(x.getTransactionHistory(),
                        x.getDate(), newBudgets.asUnmodifiableObservableList(), x.getCategory()));
                }
            }
        });
    }

    @Override
    public void updateProjectionsAfterDelete(BankAccountOperation deleted) {
        this.getFilteredProjectionsList().forEach(x -> {
            if (x.isGeneral()) {
                this.delete(x);
                UniqueTransactionList newTransactions = new UniqueTransactionList();
                newTransactions.setTransactions(x.getTransactionHistory());
                newTransactions.remove(deleted);
                if (newTransactions.asUnmodifiableObservableList().size()
                    >= ProjectCommand.REQUIRED_MINIMUM_TRANSACTIONS) {
                    this.add(new Projection(newTransactions.asUnmodifiableObservableList(),
                        x.getDate(), x.getBudgets()));
                }
            } else {
                boolean sameCategory = deleted.getCategories().stream().anyMatch(c -> {
                    if (x.getCategory() != null) {
                        return c.equals(x.getCategory());
                    }
                    return false;
                });
                if (sameCategory) {
                    this.delete(x);
                    UniqueTransactionList newTransactions = new UniqueTransactionList();
                    newTransactions.setTransactions(x.getTransactionHistory());
                    newTransactions.remove(deleted);
                    this.add(new Projection(newTransactions.asUnmodifiableObservableList(),
                        x.getDate(), x.getBudgets(), x.getCategory()));
                }
            }
        });
    }

    @Override
    public void updateProjectionsAfterDelete(Budget deleted) {
        this.getFilteredProjectionsList().forEach(x -> {
            if (deleted.isGeneral() && x.isGeneral()) {
                this.delete(x);
                UniqueBudgetList newBudgets = new UniqueBudgetList();
                newBudgets.setBudgets(x.getBudgets());
                newBudgets.remove(deleted);
                this.add(new Projection(this.getFilteredTransactionList(), x.getDate(),
                    newBudgets.asUnmodifiableObservableList()));
            } else {
                boolean sameCategory = deleted.getCategories().stream().anyMatch(c -> {
                    if (x.getCategory() != null) {
                        return c.equals(x.getCategory());
                    }
                    return false;
                });
                if (sameCategory) {
                    this.delete(x);
                    UniqueBudgetList newBudgets = new UniqueBudgetList();
                    newBudgets.setBudgets(x.getBudgets());
                    newBudgets.remove(deleted);
                    this.add(new Projection(x.getTransactionHistory(),
                        x.getDate(), newBudgets.asUnmodifiableObservableList(), x.getCategory()));
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

    /**
     * Updates all list with the current predicates stored in {@code VersionedUserState}.
     */
    private void updateAllPredicates() {
        this.filteredTransactions.setPredicate(versionedUserState.getCurrentTransPred());
        this.filteredBudgets.setPredicate(versionedUserState.getCurrentBudgetPred());
        this.filteredLedgerOperations.setPredicate(versionedUserState.getCurrentLedgerPred());
        this.filteredProjections.setPredicate(versionedUserState.getCurrentProjectionPred());
    }

}
