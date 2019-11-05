package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.itinerary.logic.commands.DoneEventCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;

class DoneEventCommandTest {

    private static final Index INDEX_FIRST_EVENT = Index.fromOneBased(1);
    private static final Index INDEX_SECOND_EVENT = Index.fromOneBased(2);

    @Test
    public void execute_done_command() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), MESSAGE_SUCCESS);
    }

    @Test
    public void null_index_doneCommand() {
        assertThrows(NullPointerException.class, () -> new DoneEventCommand(null));
    }

    @Test
    public void equals() {
        DoneEventCommand doneFirstCommand = new DoneEventCommand(INDEX_FIRST_EVENT);
        DoneEventCommand doneSecondCommand = new DoneEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        DoneEventCommand doneFirstCommandCopy = new DoneEventCommand(INDEX_FIRST_EVENT);
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }


}
