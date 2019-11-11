package seedu.moolah.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteEventCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        DeleteEventCommand command = new DeleteEventCommand(INDEX_FIRST);

        ModelManager expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
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
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(INDEX_FIRST);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different event -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show nothing.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
