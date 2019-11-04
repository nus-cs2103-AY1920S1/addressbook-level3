package seedu.address.model.stubs;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

/**
 * A default model stub that has all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getUserStateFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserStateFilePath(Path bankAccountFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserState(ReadOnlyUserState bankAccount) {

    }

    @Override
    public void add(BankAccountOperation transaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void add(LedgerOperation operation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void add(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyBankAccount getBankAccount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoUserState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoUserState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoUserState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoUserState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitUserState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransactions(List<BankAccountOperation> transactionHistory) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserState getUserState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean has(BankAccountOperation transaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean has(Budget budget) {
        throw new AssertionError("This method should not be calld.");
    }

    @Override
    public boolean has(LedgerOperation ledgerOperation) {
        throw new AssertionError("This method should not be calld.");
    }

    @Override
    public void deleteTransaction(BankAccountOperation target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransaction(BankAccountOperation target, BankAccountOperation editedTransaction) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBudget(Budget budgetTarget, Budget budgetEdit) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public FilteredList<BankAccountOperation> getFilteredTransactionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLedgerList(Predicate<LedgerOperation> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return null;
    }

    @Override
    public void deleteBudget(Budget budgetToDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<LedgerOperation> getFilteredLedgerOperationsList() {
        return null;
    }
}