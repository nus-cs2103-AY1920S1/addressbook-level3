package seedu.sugarmummy.model.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TimeDurationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TimeDuration((String) null));
    }

    @Test
    public void isValidTimeDuration_false() {
        assertFalse(TimeDuration.isValidTimeDuration("00:2"));
        assertFalse(TimeDuration.isValidTimeDuration("0:222"));
        assertFalse(TimeDuration.isValidTimeDuration("2222"));
    }

    @Test
    public void isValidTimeDuration_true() {
        assertTrue(TimeDuration.isValidTimeDuration("12:59"));
        assertTrue(TimeDuration.isValidTimeDuration("00:70"));
    }

    @Test
    public void constructor_string() {
        assertEquals(new TimeDuration(12, 30), new TimeDuration("12:30"));
    }
}
