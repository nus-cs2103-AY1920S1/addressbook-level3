package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.EventUtil.vEventToString;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventsRecord;

import org.junit.jupiter.api.Test;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class EventDeleteCommandTest {
    private Model model = new ModelManager();

    public EventDeleteCommandTest() {
        model.setEventRecord(getTypicalEventsRecord());
    }

    @Test
    public void execute_validIndex_success() {
        VEvent vEventToDelete = model.getVEvent(INDEX_ONE);
        EventDeleteCommand deleteCommand = new EventDeleteCommand(INDEX_ONE);

        String expectedMessage = String.format(EventDeleteCommand.MESSAGE_DELETE_VEVENT_SUCCESS,
                vEventToString(vEventToDelete));

        ModelManager expectedModel = new ModelManager();
        expectedModel.setEventRecord(getTypicalEventsRecord());

        assertCommandSuccess(deleteCommand, model, new CommandResult(expectedMessage,
                CommandResultType.SHOW_SCHEDULE), expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getVEventList().size() + 1);
        EventDeleteCommand deleteCommand = new EventDeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model), MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EventDeleteCommand deleteFirstCommand = new EventDeleteCommand(INDEX_ONE);
        EventDeleteCommand deleteSecondCommand = new EventDeleteCommand(INDEX_TWO);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        EventDeleteCommand deleteFirstCommandCopy = new EventDeleteCommand(INDEX_ONE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
