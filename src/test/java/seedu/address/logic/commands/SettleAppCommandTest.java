package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

class SettleAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SettleAppCommand(null, null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        Event eventToSettled = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event eventSettled = new EventBuilder(eventToSettled).withStatus("SETTLED").build();
        CommandResult commandResult = new SettleAppCommand(eventToSettled, eventSettled).execute(model);
        assertEquals(String.format(SettleAppCommand.MESSAGE_SUCCESS, eventSettled),
                commandResult.getFeedbackToUser());
        new SettleAppCommand(eventSettled, eventToSettled).execute(model);
    }

    @Test
    void testEquals() {
        Event event_01A = new EventBuilder().withId("01A").build();
        Event event_01A_Settled = new EventBuilder(event_01A).withStatus("SETTLED").build();

        Event event_02B = new EventBuilder().withId("02B").build();
        Event event_02B_Settled = new EventBuilder(event_02B).withStatus("SETTLED").build();

        SettleAppCommand settleCommand_01A = new SettleAppCommand(event_01A, event_01A_Settled);
        SettleAppCommand settleCommand_02B = new SettleAppCommand(event_02B, event_02B_Settled);

        // same object -> returns true
        assertTrue(settleCommand_01A.equals(settleCommand_01A));

        // same values -> returns true
        SettleAppCommand addCommand_01A_Copy = new SettleAppCommand(event_01A, event_01A_Settled);
        assertTrue(settleCommand_01A.equals(addCommand_01A_Copy));


        // different types -> returns false
        assertFalse(settleCommand_01A.equals(1));

        // null -> returns false
        assertFalse(settleCommand_01A.equals(null));

        // different event -> returns false
        assertFalse(settleCommand_01A.equals(settleCommand_02B));
    }
}