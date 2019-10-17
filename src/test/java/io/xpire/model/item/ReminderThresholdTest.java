package io.xpire.model.item;

import static io.xpire.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ReminderThresholdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderThreshold(null));
    }

    @Test
    public void constructor_invalidReminderThreshold_throwsIllegalArgumentException() {
        String invalidReminderThreshold = "";
        assertThrows(IllegalArgumentException.class, () -> new ReminderThreshold(invalidReminderThreshold));
    }

    @Test
    public void isValidReminderThreshold() {
        // null reminder threshold
        assertThrows(NullPointerException.class, () -> ReminderThreshold.isValidReminderThreshold(null));

        // invalid reminder threshold
        assertFalse(ReminderThreshold.isValidReminderThreshold("")); // empty string
        assertFalse(ReminderThreshold.isValidReminderThreshold(" ")); // spaces only
        assertFalse(ReminderThreshold.isValidReminderThreshold("i")); // only non-numeric characters
        assertFalse(ReminderThreshold.isValidReminderThreshold("3*")); // contains non-numeric characters
        assertFalse(ReminderThreshold.isValidReminderThreshold("1.5")); // contains non-integer numbers
        assertFalse(ReminderThreshold.isValidReminderThreshold("-1")); // contains non-positive numbers
        assertFalse(ReminderThreshold.isValidReminderThreshold("10000000000")); // contains very large numbers

        // valid reminder threshold
        assertTrue(ReminderThreshold.isValidReminderThreshold("0")); // zero
        assertTrue(ReminderThreshold.isValidReminderThreshold("12")); // positive integer
    }
}
