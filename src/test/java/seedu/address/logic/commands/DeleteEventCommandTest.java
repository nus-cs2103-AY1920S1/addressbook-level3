package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPerformance.EVENT_ONE;
import static seedu.address.testutil.TypicalPerformance.EVENT_TWO;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.Event;

public class DeleteEventCommandTest {

    private static final String VALID_EVENT_NAME = "freestyle 50m";
    private static final String INVALID_EVENT_NAME = "hopscotch";

    private Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(),
        new TrainingManager(), new UserPrefs(), new HistoryManager());

    @Test
    public void execute_validEvent_success() {
        Event eventToDelete = new Event(VALID_EVENT_NAME);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(eventToDelete);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, VALID_EVENT_NAME);

        ModelManager expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
            model.getTrainingManager(), new UserPrefs(), model.getHistory());

        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEvent_throwsCommandException() {
        Event eventNotInPerformance = new Event(INVALID_EVENT_NAME);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(eventNotInPerformance);

        assertCommandFailure(deleteEventCommand, model, String.format(Event.MESSAGE_NO_SUCH_EVENT, INVALID_EVENT_NAME));
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteEventFirstCommand = new DeleteEventCommand(EVENT_ONE);
        DeleteEventCommand deleteEventSecondCommand = new DeleteEventCommand(EVENT_TWO);

        // same object -> returns true
        assertTrue(deleteEventFirstCommand.equals(deleteEventFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(EVENT_ONE);
        assertTrue(deleteEventFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteEventFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteEventFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteEventFirstCommand.equals(deleteEventSecondCommand));
    }
}
