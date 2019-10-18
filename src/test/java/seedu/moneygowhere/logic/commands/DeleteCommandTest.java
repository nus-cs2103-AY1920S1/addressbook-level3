package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.showSpendingAtIndex;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_SECOND_SPENDING;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Spending spendingToDelete = model.getFilteredSpendingList().get(INDEX_FIRST_SPENDING.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SPENDING);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SPENDING_SUCCESS, spendingToDelete);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.deleteSpending(spendingToDelete);

        System.out.println(model);
        System.out.println(expectedModel);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSpendingList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSpendingAtIndex(model, INDEX_FIRST_SPENDING);

        Spending spendingToDelete = model.getFilteredSpendingList().get(INDEX_FIRST_SPENDING.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SPENDING);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SPENDING_SUCCESS, spendingToDelete);

        Model expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.deleteSpending(spendingToDelete);
        showNoSpending(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSpendingAtIndex(model, INDEX_FIRST_SPENDING);

        Index outOfBoundIndex = INDEX_SECOND_SPENDING;
        // ensures that outOfBoundIndex is still in bounds of MoneyGoWhere list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSpendingBook().getSpendingList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_SPENDING);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_SPENDING);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_SPENDING);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Spending -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSpending(Model model) {
        model.updateFilteredSpendingList(p -> false);

        assertTrue(model.getFilteredSpendingList().isEmpty());
    }
}
