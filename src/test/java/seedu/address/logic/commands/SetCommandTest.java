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
import seedu.address.model.*;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.testutil.BudgetBuilder;

public class SetCommandTest {

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetCommand(null));
    }

    @Test
    public void executeBudgetAcceptedByModeladdSuccessful() throws Exception {
        ModelStubAcceptingBudgetAdded modelStub = new ModelStubAcceptingBudgetAdded();
        Budget validBudget = new BudgetBuilder().build();

        SetCommand setCommand = new SetCommand(validBudget);
        CommandResult commandResult = setCommand.execute(modelStub);

        assertEquals(String.format(SetCommand.MESSAGE_SUCCESS, validBudget), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBudget), modelStub.budgetsAdded);
    }

    @Test
    public void equals() {
        Budget firstBudget = new BudgetBuilder()
            .withCategories("Food")
            .withAmount("100")
            .withDate("10102019")
            .build();
        Budget secondBudget = new BudgetBuilder()
            .withCategories("Drinks")
            .withAmount("80")
            .withDate("10102019")
            .build();
        SetCommand setFirstCommand = new SetCommand(firstBudget);
        SetCommand setSecondCommand = new SetCommand(secondBudget);

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns false
        SetCommand setFirstCommandCopy = new SetCommand(firstBudget);
        assertFalse(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
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
        public Path getUserStateFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserStateFilePath(Path bankAccountFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserState(ReadOnlyUserState bankAccount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOperation(BankAccountOperation transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOperation(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOperation(Budget budget) {
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

        public FilteredList<Budget> getFilteredBudgetist() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBudget(Budget budgetToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<LedgerOperation> getFilteredLedgerOperationsList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithBudget extends ModelStub {
        private final Budget budget;

        ModelStubWithBudget(Budget budget) {
            requireNonNull(budget);
            this.budget = budget;
        }

        public boolean hasBudget(Budget budget) {
            requireNonNull(budget);
            return this.budget.isSameBudget(budget);
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingBudgetAdded extends ModelStub {
        final ArrayList<Budget> budgetsAdded = new ArrayList<>();

        public boolean hasBudget(Budget budget) {
            requireNonNull(budget);
            return budgetsAdded.stream().anyMatch(budget::isSameBudget);
        }

        @Override
        public void addOperation(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOperation(Budget budget) {
            requireNonNull(budget);
            budgetsAdded.add(budget);
        }

        @Override
        public void commitUserState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return new BankAccount();
        }
    }

}


