package seedu.address.model.calendar.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TaskDayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDay(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskDay(invalidDay));
    }

    @Test
    public void isValidDay() {
        // null day
        assertThrows(NullPointerException.class, () -> TaskDay.isValidDay(null));

        // invalid days
        assertFalse(TaskDay.isValidDay(""));
        assertFalse(TaskDay.isValidDay(" "));
        assertFalse(TaskDay.isValidDay("monda"));
        assertFalse(TaskDay.isValidDay("tue"));

        // valid days
        assertTrue(TaskDay.isValidDay("monday"));
        assertTrue(TaskDay.isValidDay("Monday"));
        assertTrue(TaskDay.isValidDay("mONday"));
    }
}
