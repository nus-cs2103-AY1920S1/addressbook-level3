package seedu.address.model.calendar.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TaskTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskTime(null));
    }
    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskTime(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> TaskTime.isValidTime(null));

        // invalid addresses
        assertFalse(TaskTime.isValidTime("")); // empty string
        assertFalse(TaskTime.isValidTime(" ")); // spaces only

        // valid addresses
        assertTrue(TaskTime.isValidTime("12:00"));
        assertTrue(TaskTime.isValidTime("00:00"));
        assertTrue(TaskTime.isValidTime("23:59"));
    }

}
