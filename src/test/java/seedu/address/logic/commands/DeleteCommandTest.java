package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSpendingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SPENDING;
import static seedu.address.testutil.TypicalSpendings.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.spending.Spending;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Spending spendingToDelete = model.getFilteredSpendingList().get(INDEX_FIRST_SPENDING.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SPENDING);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SPENDING_SUCCESS, spendingToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSpending(spendingToDelete);

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

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSpending(spendingToDelete);
        showNoSpending(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSpendingAtIndex(model, INDEX_FIRST_SPENDING);

        Index outOfBoundIndex = INDEX_SECOND_SPENDING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getSpendingList().size());

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
