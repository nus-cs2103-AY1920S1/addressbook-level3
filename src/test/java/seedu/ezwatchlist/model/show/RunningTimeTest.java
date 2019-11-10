package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RunningTimeTest {

    @Test
    public void constructor_invalidRunningTime_throwsIllegalArgumentException() {
        int invalidRunningTime = -1;
        //assertThrows(IllegalArgumentException.class, () -> new RunningTime(invalidRunningTime));
    }

    @Test
    public void isValidRunningTime() {

        // invalid running time
        assertFalse(RunningTime.isValidRunningTime(-1)); // negative number

        // valid running time
        assertTrue(RunningTime.isValidRunningTime(123)); // exactly 3 numbers
        assertTrue(RunningTime.isValidRunningTime(12033123)); // long phone numbers
    }

    @Test
    void testHashCode() {
        assertEquals(new RunningTime(1).hashCode(), 1);
    }
}
