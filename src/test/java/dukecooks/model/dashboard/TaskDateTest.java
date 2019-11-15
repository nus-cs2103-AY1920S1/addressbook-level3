package dukecooks.model.dashboard;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.dashboard.components.TaskDate;

public class TaskDateTest {

    @Test
    public void constructor_invalidTaskDate_throwsIllegalArgumentException() {
        String invalidTaskDate = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskDate(invalidTaskDate));
    }

    @Test
    public void isValidTaskDate() {

        // invalid TaskDate
        assertFalse(TaskDate.isValidTaskDate("")); // empty string
        assertFalse(TaskDate.isValidTaskDate(" ")); // spaces only
        assertFalse(TaskDate.isValidTaskDate("29/2/2019")); // non leap year february month
        assertFalse(TaskDate.isValidTaskDate("31/11/2019")); // november only has 30 days
        assertFalse(TaskDate.isValidTaskDate("2/14/2019")); // no such month

        // valid TaskDate
        assertTrue(TaskDate.isValidTaskDate("28/2/2019")); // last day of february
        assertTrue(TaskDate.isValidTaskDate("30/11/2019")); // last day of november
        assertTrue(TaskDate.isValidTaskDate("1/1/2019")); // first day of january
    }
}
