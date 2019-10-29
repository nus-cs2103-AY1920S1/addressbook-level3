package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.BankAccount;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.BankOperationBuilder;

public class OutCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OutCommand(null));
    }

    @Test
    public void executeTransactionAcceptedByModeladdSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub = new ModelStubAcceptingTransactionAdded();
        BankAccountOperation validTransaction = new BankOperationBuilder().build();

        OutCommand outCommand = new OutCommand(validTransaction);
        CommandResult commandResult = outCommand.execute(modelStub);

        assertEquals(String.format(OutCommand.MESSAGE_SUCCESS, validTransaction), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTransaction), modelStub.transactionsAdded);
    }
    /*
    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        BankAccountOperation validTransaction = new BankOperationBuilder().build();
        InCommand addCommand = new InCommand(validTransaction);
        ModelStub modelStub = new ModelStubWithTransaction(validTransaction);

        assertThrows(CommandException.class, InCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }
    */

    @Test
    public void equals() {
        BankAccountOperation firstTransaction = new BankOperationBuilder()
                .withCategories("Food")
                .withAmount("100")
                .withDate("10102019")
                .build();
        BankAccountOperation secondTransaction = new BankOperationBuilder()
                .withCategories("Drinks")
                .withAmount("80")
                .withDate("10102019")
                .build();
        OutCommand addFirstCommand = new OutCommand(firstTransaction);
        OutCommand addSecondCommand = new OutCommand(secondTransaction);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        OutCommand addFirstCommandCopy = new OutCommand(firstTransaction);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getBankAccountFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBankAccountFilePath(Path bankAccountFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleOperation(BankAccountOperation transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleOperation(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(BankAccountOperation transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBankAccount(ReadOnlyBankAccount newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitBankAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactions(List<BankAccountOperation> transactionHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(BankAccountOperation transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBudget(Budget budget) {
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
        public ObservableList<Budget> getFilteredBudgetList() {
            return null;
        }

        @Override
        public void deleteBudget(Budget budgetToDelete) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithTransaction extends ModelStub {
        private final Transaction transaction;

        ModelStubWithTransaction(Transaction transaction) {
            requireNonNull(transaction);
            this.transaction = transaction;
        }

        @Override
        public boolean hasTransaction(BankAccountOperation transaction) {
            requireNonNull(transaction);
            return this.transaction.isSameTransaction(transaction);
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends ModelStub {
        final ArrayList<BankAccountOperation> transactionsAdded = new ArrayList<>();

        @Override
        public boolean hasTransaction(BankAccountOperation transaction) {
            requireNonNull(transaction);
            return transactionsAdded.stream().anyMatch(transaction::isSameTransaction);
        }

        @Override
        public void handleOperation(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleOperation(BankAccountOperation transaction) {
            addTransaction(transaction);
        }

        @Override
        public void addTransaction(BankAccountOperation transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }

        @Override
        public void commitBankAccount() {

        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return new BankAccount();
        }
    }

}
