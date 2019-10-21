package seedu.moneygowhere.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.spending.Date;

class ReminderTest {

    @Test
    public void getReminderMessage() {
        Reminder dummyReminder = new Reminder(new Date("today"), new ReminderMessage("Pay Bill"));
        assertEquals(new ReminderMessage("Pay Bill"), dummyReminder.getReminderMessage());
    }

    @Test
    public void getDeadline() {
        Reminder dummyReminder = new Reminder(new Date("30/11/2999"), new ReminderMessage("Pay Bill"));
        assertEquals(new Date("30/11/2999"), dummyReminder.getDeadline());
    }

    @Test
    public void isSameReminder() {
        Reminder firstDummyReminder = new Reminder(new Date("today"), new ReminderMessage("Pay Bill"));
        assertTrue(firstDummyReminder.isSameReminder(new Reminder(new Date("today"), new ReminderMessage("Pay Bill"))));

        Reminder secondDummyReminder = new Reminder(new Date("30/11/2999"), new ReminderMessage("Pay rental fees"));
        assertTrue(secondDummyReminder.isSameReminder(new Reminder(new Date("30/11/2999"),
                new ReminderMessage("Pay rental fees"))));
    }

    @Test
    public void isValidReminder() {
        Reminder dummyReminder = new Reminder(new Date("today"), new ReminderMessage("Pay Bill"));
        assertNotEquals(null, dummyReminder);
    }
}
