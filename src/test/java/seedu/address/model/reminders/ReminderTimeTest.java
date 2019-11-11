package seedu.address.model.reminders;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.ReminderTime;

public class ReminderTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderTime(null));
    }
}
