package mams.commons.core.time;

import static mams.testutil.TypicalTimeStamps.TIME_STAMP_1;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_2;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_3;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class TimeStampTest {
    @Test
    public void hashCodeTest() {
        TimeStamp sameTimeStamp = new TimeStamp(new Date(
                TIME_STAMP_1.asUnixTime()
        ));

        // same object -> same hashcode
        assertEquals(TIME_STAMP_1.hashCode(), TIME_STAMP_1.hashCode());

        // same internal details -> same hashcode
        assertEquals(TIME_STAMP_1.hashCode(), sameTimeStamp.hashCode());

        // different internal values -> different hashcode
        assertNotEquals(TIME_STAMP_1, TIME_STAMP_2);

        // different object types -> different hashcode
        assertNotEquals(TIME_STAMP_1, Objects.hash(0));
    }

    @Test
    public void equals() {
        TimeStamp sameTimeStamp = new TimeStamp(new Date(
                TIME_STAMP_1.asUnixTime()
        ));

        // same object -> returns true
        assertTrue(TIME_STAMP_1.equals(TIME_STAMP_1));

        // same values -> returns true
        assertTrue(TIME_STAMP_1.equals(sameTimeStamp));

        // different types -> returns false
        assertFalse(TIME_STAMP_1.equals(1));

        // null -> returns false
        assertFalse(TIME_STAMP_1.equals(null));

        // different internal values -> return false
        assertFalse(TIME_STAMP_1.equals(TIME_STAMP_2));
        assertFalse(TIME_STAMP_1.equals(TIME_STAMP_3));
        assertFalse(TIME_STAMP_1.equals(TIME_STAMP_4));
    }
}
