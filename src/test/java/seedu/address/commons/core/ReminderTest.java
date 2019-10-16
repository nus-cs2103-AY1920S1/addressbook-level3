package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Contains unit test for Reminder.
 */
class ReminderTest {

    @Test
    public void getReminderMessage() {
        Reminder dummyReminder = new Reminder("Pay Bill", new Date(2019, 10, 10));
        assertEquals("Pay Bill", dummyReminder.getReminderMessage());
    }

    @Test
    public void getDeadline() {
        Reminder dummyReminder = new Reminder("Pay Bill", new Date(2019, 10, 10));
        assertEquals(new Date(2019, 10, 10), dummyReminder.getDeadline());
    }

    @Test
    public void isValidReminder() {
        Reminder dummyReminder = new Reminder("Pay Bill", new Date(2019, 10, 10));
        assertNotEquals(null, dummyReminder);
    }

    @Test
    public void isValidDeadline() {
        Reminder dummyReminder = new Reminder("Pay Bill", new Date(2019, 10, 10));
        assertNotEquals(null, dummyReminder.getDeadline());
    }
}
