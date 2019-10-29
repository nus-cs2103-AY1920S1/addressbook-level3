package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalEvents.EVENT_ALICE;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


class ChangeAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ChangeAppCommand(null, null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        model.deleteAppointment(EVENT_BENSON);

        Event eventToChange = model.getFilteredAppointmentList().get(INDEX_SECOND_EVENT.getZeroBased());
        Event eventChanged = new EventBuilder(EVENT_BENSON).withId(eventToChange.getPersonId().toString()).build();
        CommandResult commandResult = new ChangeAppCommand(eventToChange, eventChanged).execute(model);
        assertEquals(String.format(ChangeAppCommand.MESSAGE_SUCCESS, eventChanged),
                commandResult.getFeedbackToUser());
        new ChangeAppCommand(eventChanged, eventToChange).execute(model);
    }

    @Test
    void testEquals() {
        Event event_01A = new EventBuilder().withId("01A").build();
        Event event_01A_edit = new EventBuilder(event_01A).withStartTime("12/12/19 1800").build();

        Event event_02B = new EventBuilder().withId("02B").build();
        Event event_02B_edit = new EventBuilder(event_02B).withStartTime("12/12/20 1900").build();

        ChangeAppCommand changeCommand_01A = new ChangeAppCommand(event_01A, event_01A_edit);
        ChangeAppCommand changeCommand_02B = new ChangeAppCommand(event_02B, event_02B_edit);

        // same object -> returns true
        assertTrue(changeCommand_01A.equals(changeCommand_01A));

        // same values -> returns true
        ChangeAppCommand addCommand_01A_Copy = new ChangeAppCommand(event_01A, event_01A_edit);
        assertTrue(changeCommand_01A.equals(addCommand_01A_Copy));


        // different types -> returns false
        assertFalse(changeCommand_01A.equals(1));

        // null -> returns false
        assertFalse(changeCommand_01A.equals(null));

        // different event -> returns false
        assertFalse(changeCommand_01A.equals(changeCommand_02B));
    }
}