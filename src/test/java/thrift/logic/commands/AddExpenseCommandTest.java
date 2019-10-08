package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import thrift.commons.core.GuiSettings;
import thrift.model.Model;
import thrift.model.ReadOnlyThrift;
import thrift.model.ReadOnlyUserPrefs;
import thrift.model.Thrift;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.testutil.ExpenseBuilder;

public class AddExpenseCommandTest {

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExpenseCommand(null));
    }

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub = new ModelStubAcceptingTransactionAdded();
        Expense validExpense = new ExpenseBuilder().build();

        CommandResult commandResult = new AddExpenseCommand(validExpense).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExpense), modelStub.transactionsAdded);
    }

    @Test
    public void undo_undoSuccessful() {
        ModelStubUndoAddExpenses modelStub = new ModelStubUndoAddExpenses();
        Expense validExpense = new ExpenseBuilder().build();
        modelStub.addExpense(validExpense);
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(validExpense);
        modelStub.keepTrackCommands(addExpenseCommand);
        Undoable undoable = modelStub.getPreviousUndoableCommand();
        undoable.undo(modelStub);
        assertEquals(0, modelStub.getThrift().getTransactionList().size());
        assertTrue(modelStub.undoableCommandStack.isEmpty());
    }

    @Test
    public void equals() {
        Expense one = new ExpenseBuilder().withDescription("Expense One").build();
        Expense two = new ExpenseBuilder().withDescription("Expense Two").build();
        AddExpenseCommand addOneCommand = new AddExpenseCommand(one);
        AddExpenseCommand addTwoCommand = new AddExpenseCommand(two);

        // same object -> returns true
        assertTrue(addOneCommand.equals(addOneCommand));

        // same values -> returns true
        AddExpenseCommand addOneCommandCopy = new AddExpenseCommand(one);
        assertTrue(addOneCommand.equals(addOneCommandCopy));

        // different types -> returns false
        assertFalse(addOneCommand.equals(1));

        // null -> returns false
        assertFalse(addOneCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addOneCommand.equals(addTwoCommand));
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
        public Path getThriftFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setThriftFilePath(Path thriftFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIncome(Income income) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setThrift(ReadOnlyThrift newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyThrift getThrift() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void keepTrackCommands(Undoable command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Undoable getPreviousUndoableCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUndoableCommand() {
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
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return this.transaction.isSameTransaction(transaction);
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();

        @Override
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return transactionsAdded.stream().anyMatch(transaction::isSameTransaction);
        }

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            transactionsAdded.add(expense);
        }

        @Override
        public ReadOnlyThrift getThrift() {
            return new Thrift();
        }
    }

    /**
     * A Model stub that allows user to perform undo operation.
     */
    private class ModelStubUndoAddExpenses extends ModelStub {
        final Stack<Undoable> undoableCommandStack = new Stack<>();
        final ThriftStubForUndoAddExpenses thriftStub;

        public ModelStubUndoAddExpenses() {
            thriftStub = new ThriftStubForUndoAddExpenses();
        }

        @Override
        public void keepTrackCommands(Undoable command) {
            undoableCommandStack.push(command);
        }

        @Override
        public Undoable getPreviousUndoableCommand() {
            return undoableCommandStack.pop();
        }

        @Override
        public Thrift getThrift() {
            return thriftStub;
        }

        @Override
        public void addExpense(Expense expense) {
            thriftStub.addTransaction(expense);
        }

        @Override
        public void deleteTransaction(Transaction transaction) {
            thriftStub.removeTransaction(transaction);
        }
    }

    /**
     * A Thrift stub that contains an empty list of transaction.
     */
    private class ThriftStubForUndoAddExpenses extends Thrift {
        final List<Transaction> transactionsAdded = new ArrayList<>();
        @Override
        public void removeTransaction(Transaction transaction) {
            transactionsAdded.remove(transaction);
        }

        @Override
        public void addTransaction(Transaction transaction) {
            transactionsAdded.add(transaction);
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return FXCollections.observableArrayList(transactionsAdded);
        }
    }
}
