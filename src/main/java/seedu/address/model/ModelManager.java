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
import seedu.address.model.transaction.Transaction;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final BankAccount bankAccount;
    private final FilteredList<Transaction> filteredTransactions;

    /**
     * Initializes a ModelManager with the given bankAccount and userPrefs.
     */
    public ModelManager(ReadOnlyBankAccount bankAccount, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(bankAccount, userPrefs);

        logger.fine("Initializing with bank account" + bankAccount + " and user prefs " + userPrefs);

        this.bankAccount = new BankAccount(bankAccount);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTransactions = new FilteredList<>(this.bankAccount.getTransactionHistory());
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

    // TODO: implement stubs below
    @Override
    public void setBankAccount(ReadOnlyBankAccount bankAccount) {

    }

    @Override
    public ReadOnlyBankAccount getBankAccount() {
        return bankAccount;
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        return false;
    }

    @Override
    public void deleteTransaction(Transaction transaction) {

    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {

    }
    // stubs end here

    @Override
    public void addTransaction(Transaction transaction) {
        bankAccount.addTransaction(transaction);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
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
        return bankAccount.equals(other.bankAccount)
                && userPrefs.equals(other.userPrefs);
    }

}
