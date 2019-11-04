package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.itinerary.logic.commands.DoneEventCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;

class DoneEventCommandTest {

    Index index_first_event = Index.fromOneBased(1);
    Index index_second_event = Index.fromOneBased(2);

    @Test
    public void execute_done_command() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), MESSAGE_SUCCESS);
    }

    @Test
    public void null_index_done_command() {
        assertThrows(NullPointerException.class, () -> new DoneEventCommand(null));
    }

    @Test
    public void equals() {
        DoneEventCommand doneFirstCommand = new DoneEventCommand(index_first_event);
        DoneEventCommand doneSecondCommand = new DoneEventCommand(index_second_event);

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        DoneEventCommand doneFirstCommandCopy = new DoneEventCommand(index_first_event);
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }


}
