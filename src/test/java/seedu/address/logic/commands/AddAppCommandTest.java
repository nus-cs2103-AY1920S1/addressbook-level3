package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.model.ModelManager.MESSAGE_NOT_OVERLAPPING_APPOINTMENT;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.utils.ModelAcceptingEventAddedStub;
import seedu.address.logic.commands.utils.ModelStub;
import seedu.address.logic.commands.utils.ModelWithEventStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;

class AddAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppCommand((List<Event>) null));
        assertThrows(NullPointerException.class, () -> new AddAppCommand((Event) null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        model.deleteAppointment(EVENT_BENSON);
        CommandResult commandResult = new AddAppCommand(EVENT_BENSON).execute(model);
        assertEquals(String.format(AddAppCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS, EVENT_BENSON),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event duplicateEventToadded = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        AddAppCommand addapptCommand = new AddAppCommand(duplicateEventToadded);

        assertThrows(CommandException.class,
                String.format(ModelManager.MESSAGE_NOT_OVERLAPPING_APPOINTMENT,
                        duplicateEventToadded.getEventTiming().toString()), () -> addapptCommand.execute(model));
    }

    @Test
    void testEquals() {
        Event event_01A = new EventBuilder().withId("01A").build();
        Event event_02B = new EventBuilder().withId("02B").build();

        AddAppCommand addCommand_01A = new AddAppCommand(event_01A);
        AddAppCommand addCommand_02B = new AddAppCommand(event_02B);

        // same object -> returns true
        assertTrue(addCommand_01A.equals(addCommand_01A));

        // same values -> returns true
        AddAppCommand addCommand_01A_Copy = new AddAppCommand(event_01A);
        assertTrue(addCommand_01A.equals(addCommand_01A_Copy));


        // different types -> returns false
        assertFalse(addCommand_01A.equals(1));

        // null -> returns false
        assertFalse(addCommand_01A.equals(null));

        // different person -> returns false
        assertFalse(addCommand_01A.equals(addCommand_02B));
    }
}