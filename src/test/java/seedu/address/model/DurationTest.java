package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DurationTest {

    @Test
    void parse_nonZeroPaddedInput_returnsCorrectTime() {
        Duration duration = Duration.parse("920", "1600");

        assertEquals(9, duration.getStart().getHour());
        assertEquals(20, duration.getStart().getMinute());
        assertEquals(16, duration.getEnd().getHour());
        assertEquals(0, duration.getEnd().getMinute());
    }

    @Test
    void overlaps_partialOverlap_returnsTrue() {
        Duration duration1 = Duration.parse("0010", "0101");
        Duration duration2 = Duration.parse("0100", "0200");

        assertTrue(duration1.overlaps(duration2));
        assertTrue(duration2.overlaps(duration1));
    }

    @Test
    void overlaps_fullOverlap_returnsTrue() {
        Duration duration1 = Duration.parse("0001", "1900");
        Duration duration2 = Duration.parse("0030", "0200");

        assertTrue(duration1.overlaps(duration2));
        assertTrue(duration2.overlaps(duration1));
    }

    @Test
    void overlaps_discreteTime_returnsFalse() {
        Duration duration1 = Duration.parse("0010", "0100");
        Duration duration2 = Duration.parse("0100", "0200");

        assertFalse(duration1.overlaps(duration2));
        assertFalse(duration2.overlaps(duration1));
    }

    @Test
    void equals_sameStartEndTime_returnsTrue() {
        Duration duration1 = Duration.parse("0000", "1100");
        Duration duration2 = Duration.parse("0000", "1100");

        assertEquals(duration1, duration2);
    }
}
