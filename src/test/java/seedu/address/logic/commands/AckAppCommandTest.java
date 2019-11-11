package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointments.AckApptCommand;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TestUtil;


class AckAppCommandTest {
    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AckApptCommand(null, null));
    }

    @Test
    public void execute_validUnfilteredList_success() throws Exception {
        Event eventToAcked = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event eventAcked = new EventBuilder(eventToAcked).withStatus("ACKNOWLEDGED").build();
        CommandResult commandResult = new AckApptCommand(eventToAcked, eventAcked).execute(model);
        assertEquals(String.format(AckApptCommand.MESSAGE_SUCCESS, eventAcked.getPersonId(),
                eventAcked.getPersonName(), eventAcked),
                commandResult.getFeedbackToUser());
        new AckApptCommand(eventAcked, eventToAcked).execute(model);
    }

    @Test
    void testEquals() {
        Event firstEvent = new EventBuilder(ALICE).build();
        Event firstEventAcked = new EventBuilder(firstEvent).withStatus("ACKNOWLEDGED").build();

        Event secondEvent = new EventBuilder(BENSON).build();
        Event secondEventAcked = new EventBuilder(secondEvent).withStatus("ACKNOWLEDGED").build();

        AckApptCommand firstAckCommand = new AckApptCommand(firstEvent, firstEventAcked);
        AckApptCommand secondAckCommand = new AckApptCommand(secondEvent, secondEventAcked);

        // same object -> returns true
        assertTrue(firstAckCommand.equals(firstAckCommand));

        // same values -> returns true
        AckApptCommand firstAckCommandCopy = new AckApptCommand(firstEvent, firstEventAcked);
        assertTrue(firstAckCommand.equals(firstAckCommandCopy));


        // different types -> returns false
        assertFalse(firstAckCommand.equals(1));

        // null -> returns false
        assertFalse(firstAckCommand.equals(null));

        // different event -> returns false
        assertFalse(firstAckCommand.equals(secondAckCommand));
    }
}
