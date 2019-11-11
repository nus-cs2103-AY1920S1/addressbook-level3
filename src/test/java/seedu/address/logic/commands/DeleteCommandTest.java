package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;
import static seedu.address.testutil.TypicalTypes.TYPE_TRANSACTION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.ui.tab.Tab;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalUserState(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        BankAccountOperation transactionToDelete = model
            .getFilteredTransactionList()
            .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TYPE_TRANSACTION, INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTRY_SUCCESS, transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
        expectedModel.delete(transactionToDelete);
        expectedModel.updateProjectionsAfterDelete(transactionToDelete);
        expectedModel.commitUserState();
        assertCommandSuccess(deleteCommand, model,
            new CommandResult(expectedMessage, false, false, Tab.TRANSACTION), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(TYPE_TRANSACTION, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);

        BankAccountOperation transactionToDelete = model
            .getFilteredTransactionList()
            .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TYPE_TRANSACTION, INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTRY_SUCCESS, transactionToDelete);

        Model expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
        expectedModel.delete(transactionToDelete);
        // showNoTransaction(expectedModel);
        expectedModel.commitUserState();
        assertCommandSuccess(deleteCommand, model,
            new CommandResult(expectedMessage, false, false, Tab.TRANSACTION), expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of bank account list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBankAccount().getTransactionHistory().size());

        DeleteCommand deleteCommand = new DeleteCommand(TYPE_TRANSACTION, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TYPE_TRANSACTION, INDEX_FIRST_TRANSACTION);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TYPE_TRANSACTION, INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TYPE_TRANSACTION, INDEX_FIRST_TRANSACTION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTransaction(Model model) {
        model.updateFilteredTransactionList(p -> false);

        assertTrue(model.getFilteredTransactionList().isEmpty());
    }
}
