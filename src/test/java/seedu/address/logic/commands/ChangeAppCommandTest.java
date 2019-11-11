package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT_BENSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.EditApptCommand;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


class ChangeAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditApptCommand(null, null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        model.deleteAppointment(EVENT_BENSON);
        Event eventToChange = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event eventChanged = new EventBuilder(EVENT_BENSON)
                .withId(eventToChange.getPersonId()).build();
        CommandResult commandResult = new EditApptCommand(eventToChange, eventChanged).execute(model);
        assertEquals(String.format(EditApptCommand.MESSAGE_SUCCESS, eventChanged.getPersonId(),
                eventChanged.getPersonName(), eventToChange.getEventTiming(), eventChanged.getEventTiming()),
                commandResult.getFeedbackToUser());
        new EditApptCommand(eventChanged, eventToChange).execute(model);
    }

    @Test
    void testEquals() {
        Event firstEvent = new EventBuilder(ALICE).build();
        Event firstEventEdit = new EventBuilder(firstEvent).withStartTime("12/12/19 1800").build();

        Event secondEvent = new EventBuilder(BENSON).build();
        Event secondEventEdit = new EventBuilder(secondEvent).withStartTime("12/12/20 1900").build();

        EditApptCommand firstChangeCommand = new EditApptCommand(firstEvent, firstEventEdit);
        EditApptCommand secondChangeCommand = new EditApptCommand(secondEvent, secondEventEdit);

        // same object -> returns true
        assertTrue(firstChangeCommand.equals(firstChangeCommand));

        // same values -> returns true
        EditApptCommand firstChangeCommandCopy = new EditApptCommand(firstEvent, firstEventEdit);
        assertTrue(firstChangeCommand.equals(firstChangeCommandCopy));


        // different types -> returns false
        assertFalse(firstChangeCommand.equals(1));

        // null -> returns false
        assertFalse(firstChangeCommand.equals(null));

        // different event -> returns false
        assertFalse(firstChangeCommand.equals(secondChangeCommand));
    }
}
