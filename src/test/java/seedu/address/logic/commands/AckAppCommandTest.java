package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


class AckAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AckAppCommand(null, null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        Event eventToAcked = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event eventAcked = new EventBuilder(eventToAcked).withStatus("ACKNOWLEDGED").build();
        CommandResult commandResult = new AckAppCommand(eventToAcked, eventAcked).execute(model);
        assertEquals(String.format(AckAppCommand.MESSAGE_SUCCESS, eventAcked),
                commandResult.getFeedbackToUser());
        new AckAppCommand(eventAcked, eventToAcked).execute(model);
    }

    @Test
    void testEquals() {
        Event event_01A = new EventBuilder().withId("01A").build();
        Event event_01A_Acked = new EventBuilder(event_01A).withStatus("ACKNOWLEDGED").build();

        Event event_02B = new EventBuilder().withId("02B").build();
        Event event_02B_Acked = new EventBuilder(event_02B).withStatus("ACKNOWLEDGED").build();

        AckAppCommand ackCommand_01A = new AckAppCommand(event_01A, event_01A_Acked);
        AckAppCommand ackCommand_02B = new AckAppCommand(event_02B, event_02B_Acked);

        // same object -> returns true
        assertTrue(ackCommand_01A.equals(ackCommand_01A));

        // same values -> returns true
        AckAppCommand addCommand_01A_Copy = new AckAppCommand(event_01A, event_01A_Acked);
        assertTrue(ackCommand_01A.equals(addCommand_01A_Copy));


        // different types -> returns false
        assertFalse(ackCommand_01A.equals(1));

        // null -> returns false
        assertFalse(ackCommand_01A.equals(null));

        // different event -> returns false
        assertFalse(ackCommand_01A.equals(ackCommand_02B));
    }
}