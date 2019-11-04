package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    void isValidTime() {
        // invalid time
        assertFalse(Time.isValidTime("")); // empty string

        assertFalse(Time.isValidTime("-1000")); // Negative integer

        assertFalse(Time.isValidTime("a")); //single character given

        assertFalse(Time.isValidTime("235")); // supposedly 2350 but missing the 0

        assertFalse(Time.isValidTime("235")); // supposedly 0235 but missing the 0

        assertFalse(Time.isValidTime("2370")); // Invalid 24hrs

        assertFalse(Time.isValidTime("2400")); // Invalid 24hrs

        assertFalse(Time.isValidTime("2450")); // Invalid 24hrs

        assertFalse(Time.isValidTime("8:00 a.m.")); // Incorrect input format

        assertFalse(Time.isValidTime("8.00 am")); // Incorrect input format

        assertFalse(Time.isValidTime("8.00 a.m.")); // Incorrect input format

        assertTrue(Time.isValidTime("2000")); // Over 12hrs

        assertTrue(Time.isValidTime("0000")); // Midnight

        assertTrue(Time.isValidTime("0900")); // Within 12hrs

    }

    @Test
    void testToString() {
        Time time = new Time("2000");
        Time time1 = new Time("0000");
        Time time2 = new Time("0900");

        // Testing different expected values
        // Unformatted time
        assertNotEquals("2000", time.toString());

        assertNotEquals("0000", time.toString());

        assertNotEquals("0900", time.toString());

        // Only valid time stamps that pass thee validation test will be created
        assertEquals("8:00 p.m.", time.toString());

        assertEquals("12:00 a.m.", time1.toString());

        assertEquals("9:00 a.m.", time2.toString());

    }
}
