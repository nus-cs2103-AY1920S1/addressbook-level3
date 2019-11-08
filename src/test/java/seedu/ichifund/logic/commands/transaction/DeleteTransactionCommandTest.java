package seedu.ichifund.logic.commands.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ichifund.testutil.TypicalFundBook.getTypicalFundBook;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.UserPrefs;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTransactionCommand}.
 */
public class DeleteTransactionCommandTest {

    private Model model = new ModelManager(getTypicalFundBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(INDEX_FIRST.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getFundBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(outOfBoundIndex);

        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTransactionCommand deleteFirstCommand = new DeleteTransactionCommand(INDEX_FIRST);
        DeleteTransactionCommand deleteSecondCommand = new DeleteTransactionCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTransactionCommand deleteFirstCommandCopy = new DeleteTransactionCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
