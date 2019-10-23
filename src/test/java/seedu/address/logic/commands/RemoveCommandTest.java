package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeadlineAtIndex;
import static seedu.address.testutil.TypicalDeadlines.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEADLINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DEADLINE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deadline.Deadline;

//@@author: dalsontws
/**
 * Contains unit tests for {@code RemoveCommand}.
 */
public class RemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Deadline deadlineToDelete = model.getFilteredDeadlineList().get(INDEX_FIRST_DEADLINE.getZeroBased());
        RemoveCommand removeCommand = new RemoveCommand(INDEX_FIRST_DEADLINE);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_DELETE_DEADLINE_SUCCESS, deadlineToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDeadline(deadlineToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeadlineList().size() + 1);
        RemoveCommand removeCommand = new RemoveCommand(outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeadlineAtIndex(model, INDEX_FIRST_DEADLINE);

        Deadline deadlineToDelete = model.getFilteredDeadlineList().get(INDEX_FIRST_DEADLINE.getZeroBased());
        RemoveCommand removeCommand = new RemoveCommand(INDEX_FIRST_DEADLINE);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_DELETE_DEADLINE_SUCCESS, deadlineToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDeadline(deadlineToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDeadlineAtIndex(model, INDEX_FIRST_DEADLINE);

        Index outOfBoundIndex = INDEX_SECOND_DEADLINE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getDeadlineList().size());

        RemoveCommand removeCommand = new RemoveCommand(outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemoveCommand removeFirstCommand = new RemoveCommand(INDEX_FIRST_DEADLINE);
        RemoveCommand removeSecondCommand = new RemoveCommand(INDEX_SECOND_DEADLINE);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveCommand removeFirstCommandCopy = new RemoveCommand(INDEX_FIRST_DEADLINE);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different flashCard -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredDeadlineList(p -> false);

        assertTrue(model.getFilteredDeadlineList().isEmpty());
    }
}
