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

    @Test void isOverdueReminder() {
        Reminder dummyReminder = new Reminder(new Date("yesterday"), new ReminderMessage("Pay School Fee"));
        assertEquals(ReminderType.OVERDUE, dummyReminder.getType());
    }

    @Test void isTodayDeadlinedReminder() {
        Reminder dummyReminder = new Reminder(new Date("Today"), new ReminderMessage("Pay Phone Bill"));
        assertEquals(ReminderType.DEADLINED_TODAY, dummyReminder.getType());
    }

    @Test void isTomorrowDeadlinedReminder() {
        Reminder dummyReminder = new Reminder(new Date("tomorrow"), new ReminderMessage("Repay friends"));
        assertEquals(ReminderType.DEADLINED_TOMORROW, dummyReminder.getType());
    }

    @Test void isTwoDaysLaterDeadlinedReminder() {
        Reminder dummyReminder = new Reminder(new Date("two days later"), new ReminderMessage("Repay to Johnny"));
        assertEquals(ReminderType.DEADLINED_FURTHER, dummyReminder.getType());
    }

    @Test void isOneYearLaterDeadlinedReminder() {
        Reminder dummyReminder = new Reminder(new Date("one year later"), new ReminderMessage("Pay admin fees"));
        assertEquals(ReminderType.DEADLINED_FURTHER, dummyReminder.getType());
    }
}

