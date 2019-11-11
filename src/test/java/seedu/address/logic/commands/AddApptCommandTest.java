package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.AddApptCommand;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;

class AddApptCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddApptCommand((List<Event>) null));
        assertThrows(NullPointerException.class, () -> new AddApptCommand((Event) null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        model.deleteAppointment(EVENT_BENSON);
        CommandResult commandResult = new AddApptCommand(EVENT_BENSON).execute(model);
        assertEquals(String.format(AddApptCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS, EVENT_BENSON.getPersonId(),
                EVENT_BENSON.getPersonName(), EVENT_BENSON.getEventTiming()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event duplicateEventToadded = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        AddApptCommand addapptCommand = new AddApptCommand(duplicateEventToadded);

        assertThrows(CommandException.class,
                String.format(ModelManager.MESSAGE_NOT_OVERLAPPING_APPOINTMENT,
                        duplicateEventToadded.getPersonName().toString(),
                        duplicateEventToadded.getEventTiming().toString()), () -> addapptCommand.execute(model));
    }

    @Test
    void testEquals() {
        Event firstEvent = new EventBuilder(ALICE).build();
        Event secondEvent = new EventBuilder(BENSON).build();

        AddApptCommand firstAddCommand = new AddApptCommand(firstEvent);
        AddApptCommand secondAddCommand = new AddApptCommand(secondEvent);

        // same object -> returns true
        assertTrue(firstAddCommand.equals(firstAddCommand));

        // same values -> returns true
        AddApptCommand firstAddCommandCopy = new AddApptCommand(firstEvent);
        assertTrue(firstAddCommand.equals(firstAddCommandCopy));


        // different types -> returns false
        assertFalse(firstAddCommand.equals(1));

        // null -> returns false
        assertFalse(firstAddCommand.equals(null));

        // different person -> returns false
        assertFalse(firstAddCommand.equals(secondAddCommand));
    }
}
