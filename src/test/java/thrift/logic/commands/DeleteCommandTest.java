package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.commands.CommandTestUtil.assertCommandFailure;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.assertUndoCommandSuccess;
import static thrift.logic.commands.CommandTestUtil.showTransactionAtIndex;

import org.junit.jupiter.api.Test;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.UserPrefs;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs(),
            new PastUndoableCommands());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete);

        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());
        expectedModel.deleteTransaction(transactionToDelete);
        showNoTransaction(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of thrift list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThrift().getTransactionList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void undo_undoDeleteExpenseOnFullList_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased());

        //ensure that the first transaction is an expense.
        assertTrue(transactionToDelete instanceof Expense);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete);
        expectedModel.deleteTransaction(transactionToDelete);

        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        expectedModel.addExpense((Expense) transactionToDelete, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        assertUndoCommandSuccess(deleteCommand, model, expectedModel);
    }

    @Test
    public void undo_undoDeleteIncomeOnFullList_success() throws CommandException {
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs(),
                new PastUndoableCommands());

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(TypicalIndexes.INDEX_SECOND_TRANSACTION.getZeroBased());

        //ensure that the third transaction is an income.
        assertTrue(transactionToDelete instanceof Income);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete);
        expectedModel.deleteTransaction(transactionToDelete);
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        expectedModel.addIncome((Income) transactionToDelete, TypicalIndexes.INDEX_SECOND_TRANSACTION);
        assertUndoCommandSuccess(deleteCommand, model, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTransaction(Model model) {
        model.updateFilteredTransactionList(p -> false);

        assertTrue(model.getFilteredTransactionList().isEmpty());
    }
}
