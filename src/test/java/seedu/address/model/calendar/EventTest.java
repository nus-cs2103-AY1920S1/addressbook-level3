package seedu.address.model.calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalEvents.APPOINTMENT;
import static seedu.address.testutil.TypicalEvents.AUTO_REMINDER;
import static seedu.address.testutil.TypicalEvents.DATE_FEB10;
import static seedu.address.testutil.TypicalEvents.DATE_MAR22;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.TIME_EVEN;
import static seedu.address.testutil.TypicalEvents.TIME_MORN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

class EventTest {
    @Test
    void isSameEvent() {
        // same object -> returns true
        assertTrue(APPOINTMENT.isSameEvent(APPOINTMENT));

        // null -> returns false
        assertFalse(APPOINTMENT.isSameEvent(null));

        // different description and date, time -> returns false
        Event editedEvent = new EventBuilder(APPOINTMENT).withDescription("description")
                .withDateTime(DATE_MAR22, TIME_MORN).build();
        assertFalse(APPOINTMENT.isSameEvent(editedEvent));

        // different date and time -> returns false
        editedEvent = new EventBuilder(APPOINTMENT).withDateTime(DATE_MAR22, TIME_EVEN).build();
        assertFalse(APPOINTMENT.isSameEvent(editedEvent));

        // different description
        editedEvent = new EventBuilder(APPOINTMENT).withDescription("description").build();
        assertFalse(APPOINTMENT.isSameEvent(editedEvent));

        // same description and date, time, different endingDateTime -> returns true
        editedEvent = new EventBuilder(APPOINTMENT).withEndingDateTime(null).build();
        assertTrue(APPOINTMENT.isSameEvent(editedEvent));

        // same description and date, time, different autoReminder -> returns true
        editedEvent = new EventBuilder(APPOINTMENT).withAutoReminder(null).build();
        assertTrue(APPOINTMENT.isSameEvent(editedEvent));
        editedEvent = new EventBuilder(APPOINTMENT).withAutoReminder(AUTO_REMINDER).build();
        assertTrue(APPOINTMENT.isSameEvent(editedEvent));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event appointmentCopy = new EventBuilder(APPOINTMENT).build();
        assertTrue(APPOINTMENT.equals(appointmentCopy));

        // same object -> returns true
        assertTrue(APPOINTMENT.equals(APPOINTMENT));

        // null -> returns false
        assertFalse(APPOINTMENT.equals(null));

        // different type -> returns false
        assertFalse(APPOINTMENT.equals(5));

        // different event -> returns false
        assertFalse(APPOINTMENT.equals(MEETING));

        // different description -> returns false
        Event editedEvent = new EventBuilder(APPOINTMENT).withDescription("description").build();
        assertFalse(APPOINTMENT.equals(editedEvent));

        // different date, time -> returns false
        editedEvent = new EventBuilder(APPOINTMENT).withDateTime(DATE_FEB10, TIME_EVEN).build();
        assertFalse(APPOINTMENT.equals(editedEvent));

        // different ending date time -> returns false
        editedEvent = new EventBuilder(APPOINTMENT).withEndingDateTime(DATE_FEB10, TIME_EVEN).build();
        assertFalse(APPOINTMENT.equals(editedEvent));

        // different autoReminder -> return false
        editedEvent = new EventBuilder(APPOINTMENT).withAutoReminder(AUTO_REMINDER).build();
        assertFalse(APPOINTMENT.equals(editedEvent));
    }
}
