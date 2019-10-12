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
import thrift.commons.core.index.Index;
import thrift.model.Model;
import thrift.model.ReadOnlyThrift;
import thrift.model.ReadOnlyUserPrefs;
import thrift.model.Thrift;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.testutil.IncomeBuilder;

public class AddIncomeCommandTest {

    @Test
    public void constructor_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddIncomeCommand(null));
    }

    @Test
    public void execute_incomeAcceptedByModel_addSuccessful() {
        ModelStubAcceptingTransactionAdded modelStub = new ModelStubAcceptingTransactionAdded();
        Income validIncome = new IncomeBuilder().build();

        CommandResult commandResult = new AddIncomeCommand(validIncome).execute(modelStub);

        assertEquals(String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIncome), modelStub.transactionsAdded);
    }

    @Test
    public void undo_undoSuccessful() {
        ModelStubUndoAddIncome modelStub = new ModelStubUndoAddIncome();

        Income validIncome = new IncomeBuilder().build();

        modelStub.addIncome(validIncome);
        AddIncomeCommand addIncomeCommand = new AddIncomeCommand(validIncome);
        modelStub.keepTrackCommands(addIncomeCommand);
        assertEquals(1, modelStub.getThrift().getTransactionList().size());
        assertFalse(modelStub.undoableCommandStack.isEmpty());

        Undoable undoable = modelStub.getPreviousUndoableCommand();
        undoable.undo(modelStub);
        assertEquals(0, modelStub.getThrift().getTransactionList().size());
        assertTrue(modelStub.undoableCommandStack.isEmpty());
    }

    @Test
    public void equals() {
        Income one = new IncomeBuilder().withDescription("Income One").build();
        Income two = new IncomeBuilder().withDescription("Income Two").build();
        AddIncomeCommand addOneCommand = new AddIncomeCommand(one);
        AddIncomeCommand addTwoCommand = new AddIncomeCommand(two);

        // same object -> returns true
        assertTrue(addOneCommand.equals(addOneCommand));

        // same values -> returns true
        AddIncomeCommand addOneCommandCopy = new AddIncomeCommand(one);
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
        public void addExpense(Expense expense, Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIncome(Income income) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addIncome(Income income, Index index) {
            throw new AssertionError("This method should not be called.");
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
        public void setTransaction(Transaction target, Transaction updatedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Transaction getLastTransactionFromThrift() {
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

        @Override
        public Undoable getUndoneCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUndoneCommand() {
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
        public void addIncome(Income income) {
            requireNonNull(income);
            transactionsAdded.add(income);
        }

        @Override
        public ReadOnlyThrift getThrift() {
            return new Thrift();
        }
    }

    private class ModelStubUndoAddIncome extends ModelStub {
        final Stack<Undoable> undoableCommandStack = new Stack<>();
        final ThriftStubForUndoAddIncome thriftStub;

        public ModelStubUndoAddIncome() {
            thriftStub = new ThriftStubForUndoAddIncome();
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
        public void addIncome(Income income) {
            thriftStub.addTransaction(income);
        }

        @Override
        public void deleteTransaction(Transaction transaction) {
            thriftStub.removeTransaction(transaction);
        }

        @Override
        public Transaction getLastTransactionFromThrift() {
            return thriftStub.getLastTransaction();
        }
    }

    /**
     * A Thrift stub that contains an empty list of transaction.
     */
    private class ThriftStubForUndoAddIncome extends Thrift {
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

        @Override
        public Transaction getLastTransaction() {
            return transactionsAdded.get(transactionsAdded.size() - 1);
        }
    }
}
