package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CancelAppCommandTest {

    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CancelAppCommand((List<Event>) null));
        assertThrows(NullPointerException.class, () -> new CancelAppCommand((Event) null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        Event eventToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        CommandResult commandResult = new CancelAppCommand(eventToDelete).execute(model);
        assertEquals(String.format(CancelAppCommand.MESSAGE_CANCEL_APPOINTMENT_SUCCESS, eventToDelete),
                commandResult.getFeedbackToUser());
    }

    @Test
    void testEquals() {
        Event event_01A = new EventBuilder().withId("01A").build();
        Event event_02B = new EventBuilder().withId("02B").build();

        CancelAppCommand cancelCommand_01A = new CancelAppCommand(event_01A);
        CancelAppCommand cancelCommand_02B = new CancelAppCommand(event_02B);

        // same object -> returns true
        assertTrue(cancelCommand_01A.equals(cancelCommand_01A));

        // same values -> returns true
        CancelAppCommand cancelCommand_01A_Copy = new CancelAppCommand(event_01A);
        assertTrue(cancelCommand_01A.equals(cancelCommand_01A_Copy));


        // different types -> returns false
        assertFalse(cancelCommand_01A.equals(1));

        // null -> returns false
        assertFalse(cancelCommand_01A.equals(null));

        // different person -> returns false
        assertFalse(cancelCommand_01A.equals(cancelCommand_02B));
    }
}