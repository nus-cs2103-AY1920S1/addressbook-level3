package dukecooks.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    void isValidTime() {
        // valid time
        assertTrue(Time.isValidTime("00:00"));
        assertTrue(Time.isValidTime("23:59"));

        // invalid hours
        assertFalse(Time.isValidTime("222:20"));
        assertFalse(Time.isValidTime("   :59"));
        assertFalse(Time.isValidTime("aa:22"));

        // invalid minutes
        assertFalse(Time.isValidTime("12:aa"));
        assertFalse(Time.isValidTime("22:222"));
        assertFalse(Time.isValidTime("21:  "));

        // invalid hour and minutes
        assertFalse(Time.isValidTime("222:222"));
        assertFalse(Time.isValidTime("aa:aa"));
        assertFalse(Time.isValidTime("     "));
    }

    @Test
    void generateTime() {
        Time toCompare = new Time(1, 1);
        assertTrue(toCompare.equals(Time.generateTime("01:01")));
        assertFalse(toCompare.equals(Time.generateTime("10:10")));
    }

    @Test
    void isValidTimeFormat() {
    }

    @Test
    void isValidHour() {
        // valid hour
        assertTrue(Time.isValidHour(0));
        assertTrue(Time.isValidHour(23));

        // invalid hour
        assertFalse(Time.isValidHour(24));
        assertFalse(Time.isValidHour(-100));
        assertFalse(Time.isValidHour(100));
    }

    @Test
    void isValidMinute() {
        //valid minute
        assertTrue(Time.isValidMinute(0));
        assertTrue(Time.isValidMinute(59));

        //invalid minute
        assertFalse(Time.isValidMinute(60));
        assertFalse(Time.isValidMinute(-100));
        assertFalse(Time.isValidMinute(100));
    }

    @Test
    void testEquals() {
        Time time = new Time(23, 59);
        Time time2 = new Time(23, 59);
        Time time3 = new Time(2, 59);

        // same objects
        assertTrue(time.equals(time));

        // different objects, same fields
        assertTrue(time.equals(time2));

        // different objects and fields
        assertFalse(time2.equals(null));
        assertFalse(time2.equals(time3));
    }

    @Test
    void testToString() {
        Time time = new Time(1, 59);
        assertTrue("01:59".equals(time.toString()));
    }
}
