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
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final VersionedBankAccount versionedBankAccount;
    private final FilteredList<BankAccountOperation> filteredTransactions;
    private final FilteredList<Budget> filteredBudgets;

    /**
     * Initializes a ModelManager with the given bankAccount and userPrefs.
     */
    public ModelManager(ReadOnlyBankAccount bankAccount, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(bankAccount, userPrefs);

        logger.fine("Initializing with bank account" + bankAccount + " and user prefs " + userPrefs);

        this.versionedBankAccount = new VersionedBankAccount(bankAccount);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTransactions = new FilteredList<>(this.versionedBankAccount.getTransactionHistory());
        filteredBudgets = new FilteredList<>(this.versionedBankAccount.getBudgetHistory());
    }

    public ModelManager() {
        this(new BankAccount(), new UserPrefs());
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
    public Path getBankAccountFilePath() {
        return userPrefs.getBankAccountFilePath();
    }

    @Override
    public void setBankAccountFilePath(Path bankAccountFilePath) {
        requireNonNull(bankAccountFilePath);
        userPrefs.setBankAccountFilePath(bankAccountFilePath);
    }

    @Override
    public void setBankAccount(ReadOnlyBankAccount bankAccount) {
        requireNonNull(bankAccount);
        this.versionedBankAccount.resetData(bankAccount);
    }

    @Override
    public ReadOnlyBankAccount getBankAccount() {
        return versionedBankAccount;
    }

    @Override
    public boolean hasTransaction(BankAccountOperation transaction) {
        requireNonNull(transaction);
        return versionedBankAccount.hasTransaction(transaction);
    }

    @Override
    public void deleteTransaction(BankAccountOperation transaction) {
        versionedBankAccount.removeTransaction(transaction);
    }

    @Override
    public void deleteBudget(Budget budget) {
        versionedBankAccount.removeBudget(budget);
    }

    @Override
    public void setTransaction(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        requireAllNonNull(transactionTarget, transactionEdit);

        versionedBankAccount.setTransaction(transactionTarget, transactionEdit);
    }

    @Override
    public void addBudget(Budget budget) {
        versionedBankAccount.addBudget(budget);
    }

    @Override
    public void addTransaction(BankAccountOperation transaction) {
        versionedBankAccount.addTransaction(transaction);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     * @return
     */
    @Override
    public FilteredList<BankAccountOperation> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudgets;
    }
    @Override
    public boolean canUndoBankAccount() {
        return versionedBankAccount.canUndo();
    }

    @Override
    public void undoBankAccount() {
        versionedBankAccount.undo();
    }

    @Override
    public boolean canRedoBankAccount() {
        return versionedBankAccount.canRedo();
    }

    @Override
    public void redoBankAccount() {
        versionedBankAccount.redo();
    }

    @Override
    public void commitBankAccount() {
        versionedBankAccount.commit();
    }

    @Override
    public void setTransactions(List<BankAccountOperation> transactionHistory) {
        versionedBankAccount.setTransactions(transactionHistory);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void handleOperation(BankAccountOperation operation) {
        versionedBankAccount.addTransaction(operation);
    }

    @Override
    public void handleOperation(LedgerOperation operation) {
        versionedBankAccount.addLoan(operation);
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
        return versionedBankAccount.equals(other.versionedBankAccount)
                && userPrefs.equals(other.userPrefs)
                && filteredTransactions.equals(other.filteredTransactions);
    }

}
