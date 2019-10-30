package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    void isValidTime() {
        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime("-1000"));
        assertFalse(Time.isValidTime("a"));
        assertFalse(Time.isValidTime("2400"));
        assertFalse(Time.isValidTime("2450"));
        assertFalse(Time.isValidTime("8:00 a.m."));
        assertFalse(Time.isValidTime("8.00 am"));
        assertFalse(Time.isValidTime("8.00 a.m."));

        // valid time 
        assertTrue(Time.isValidTime("2000")); // Over 12hrs
        assertTrue(Time.isValidTime("0000")); // Midnight
        assertTrue(Time.isValidTime("0900")); // Within 12hrs
    }

    @Test
    void testToString() {
        Time time = new Time("2000");
        Time time1 = new Time("0000");
        assertEquals(time.toString(), "8:00 p.m.");
        assertEquals(time1.toString(), "12:00 a.m.");
    }
}
