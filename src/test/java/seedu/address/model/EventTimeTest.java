package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventTimeTest {

    @Test
    void parse_nonZeroPaddedInput_returnsCorrectTime() {
        EventTime eventTime = EventTime.parse("920", "1600");

        assertEquals(9, eventTime.getStart().getHour());
        assertEquals(20, eventTime.getStart().getMinute());
        assertEquals(16, eventTime.getEnd().getHour());
        assertEquals(0, eventTime.getEnd().getMinute());
    }

    @Test
    void overlaps_partialOverlap_returnsTrue() {
        EventTime eventTime1 = EventTime.parse("0010", "0101");
        EventTime eventTime2 = EventTime.parse("0100", "0200");

        assertTrue(eventTime1.overlaps(eventTime2));
        assertTrue(eventTime2.overlaps(eventTime1));
    }

    @Test
    void overlaps_fullOverlap_returnsTrue() {
        EventTime eventTime1 = EventTime.parse("0001", "1900");
        EventTime eventTime2 = EventTime.parse("0030", "0200");

        assertTrue(eventTime1.overlaps(eventTime2));
        assertTrue(eventTime2.overlaps(eventTime1));
    }

    @Test
    void overlaps_discreteTime_returnsFalse() {
        EventTime eventTime1 = EventTime.parse("0010", "0100");
        EventTime eventTime2 = EventTime.parse("0100", "0200");

        assertFalse(eventTime1.overlaps(eventTime2));
        assertFalse(eventTime2.overlaps(eventTime1));
    }

    @Test
    void equals_sameStartEndTime_returnsTrue() {
        EventTime eventTime1 = EventTime.parse("0000", "1100");
        EventTime eventTime2 = EventTime.parse("0000", "1100");

        assertEquals(eventTime1, eventTime2);
    }
}
