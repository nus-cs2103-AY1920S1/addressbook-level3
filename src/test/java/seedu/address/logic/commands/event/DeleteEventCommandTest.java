package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Event;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());

    @Test
    public void run_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("");
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    //    @Test
    //    public void run_validIndexFilteredList_success() {
    //        showEventAtIndex(model, INDEX_FIRST);
    //
    //        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
    //        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST);
    //
    //        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);
    //
    //        Model expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
    //        expectedModel.deleteEvent(eventToDelete);
    //        expectedModel.setModelHistory(new ModelHistory("", makeModelStack(model), makeModelStack()));
    //        showNoEvent(expectedModel);
    //
    //        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void run_invalidIndexFilteredList_throwsCommandException() {
    //        showEventAtIndex(model, INDEX_FIRST);
    //
    //        Index outOfBoundIndex = INDEX_SECOND;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(
    //                outOfBoundIndex.getZeroBased()
    //                < model.getMooLah().getEventList().size());
    //
    //        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(INDEX_FIRST);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
