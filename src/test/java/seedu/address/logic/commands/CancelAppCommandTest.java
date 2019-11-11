package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.CancelApptCommand;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


class CancelAppCommandTest {

    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CancelApptCommand((List<Event>) null));
        assertThrows(NullPointerException.class, () -> new CancelApptCommand((Event) null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        Event eventToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        CommandResult commandResult = new CancelApptCommand(eventToDelete).execute(model);
        assertEquals(String.format(CancelApptCommand.MESSAGE_CANCEL_APPOINTMENT_SUCCESS, eventToDelete.getPersonId(),
                eventToDelete.getPersonName(), eventToDelete.getEventTiming()),
                commandResult.getFeedbackToUser());
    }

    @Test
    void testEquals() {
        Event firstEvent = new EventBuilder(ALICE).build();
        Event secondEvent = new EventBuilder(BENSON).build();

        CancelApptCommand firstCancelCommand = new CancelApptCommand(firstEvent);
        CancelApptCommand secondCancelCommand = new CancelApptCommand(secondEvent);

        // same object -> returns true
        assertTrue(firstCancelCommand.equals(firstCancelCommand));

        // same values -> returns true
        CancelApptCommand firstCancelCommandCopy = new CancelApptCommand(firstEvent);
        assertTrue(firstCancelCommand.equals(firstCancelCommandCopy));


        // different types -> returns false
        assertFalse(firstCancelCommand.equals(1));

        // null -> returns false
        assertFalse(firstCancelCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCancelCommand.equals(secondCancelCommand));
    }
}
