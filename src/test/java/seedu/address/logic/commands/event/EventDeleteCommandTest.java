package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.EventUtil.vEventToString;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.event.TypicalVEvents.VEVENT1;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.testutil.model.ModelStub;

/**
 * Test for event delete command.
 */
public class EventDeleteCommandTest {

    @Test
    public void execute_validIndex_success() throws Exception {
        ModelStubDeleteVEvent model = new ModelStubDeleteVEvent(VEVENT1);
        VEvent vEventToDelete = model.getVEvent(INDEX_ONE);
        EventDeleteCommand deleteCommand = new EventDeleteCommand(INDEX_ONE);

        String expectedMessage = String.format(EventDeleteCommand.MESSAGE_DELETE_VEVENT_SUCCESS,
                vEventToString(vEventToDelete));
        CommandResult commandResult = deleteCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(commandResult.getCommandResultType(), CommandResultType.SHOW_SCHEDULE);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubDeleteVEvent model = new ModelStubDeleteVEvent(VEVENT1);
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

    /**
     * A Model stub that contains a single vEvent.
     */
    private class ModelStubDeleteVEvent extends ModelStub {
        private final ObservableList<VEvent> vEventList;

        ModelStubDeleteVEvent(VEvent vEvent) {
            requireNonNull(vEvent);
            vEventList = FXCollections.observableArrayList();;
            this.vEventList.add(vEvent);
        }

        @Override
        public VEvent getVEvent(Index index) {
            requireNonNull(index);
            return vEventList.get(index.getZeroBased());
        }

        @Override
        public ObservableList<VEvent> getVEventList() {
            return this.vEventList;
        }

        @Override
        public void deleteVEvent(Index index) {
            vEventList.remove(index.getZeroBased());
        }
    }
}
