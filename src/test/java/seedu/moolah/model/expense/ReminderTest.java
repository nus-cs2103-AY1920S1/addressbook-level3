package seedu.moolah.model.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.BIRTHDAY;

import org.junit.jupiter.api.Test;

import seedu.moolah.testutil.EventBuilder;

public class ReminderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, 5));
    }

    @Test
    public void testToString() {
        Event event = new EventBuilder(BIRTHDAY).build();
        long daysLeft = 1;
        Reminder reminder = new Reminder(event, daysLeft);
        String expectedMessage = String.format("You have %d days left before this event: %s!",
                daysLeft, event.getDescription());
        assertEquals(expectedMessage, reminder.toString());
    }
}
