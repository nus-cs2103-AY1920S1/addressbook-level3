package seedu.address.model.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimingTest {

    private DateTime sampleDate1;
    private DateTime sampleDate2;
    private DateTime sampleDate3;
    private DateTime sampleDate4;

    private Timing sampleEvent1;
    private Timing sampleEvent2;
    private Timing sampleEvent3;
    private Timing sampleEvent4;

    @BeforeEach
    void setup() {
        sampleDate1 = DateTime.tryParseSimpleDateFormat("10/10/20 0100");
        sampleDate2 = DateTime.tryParseSimpleDateFormat("10/10/20 0100");
        sampleDate3 = DateTime.tryParseSimpleDateFormat("10/10/20 1900");
        sampleDate4 = DateTime.tryParseSimpleDateFormat("11/10/20 0100");

        sampleEvent1 = new Timing(sampleDate1, sampleDate3);
        sampleEvent2 = new Timing(sampleDate2, sampleDate3);
        sampleEvent3 = new Timing(sampleDate1, sampleDate4);
        sampleEvent4 = new Timing(sampleDate3, sampleDate4);
    }

    @Test
    void test_isValidTiming() {
        assertFalse(Timing.isValidTiming(sampleDate1, sampleDate1));
        assertFalse(Timing.isValidTiming(sampleDate1, sampleDate2));
        assertTrue(Timing.isValidTiming(sampleDate1, sampleDate3));
        assertTrue(Timing.isValidTiming(sampleDate1, sampleDate4));

        assertFalse(Timing.isValidTiming(sampleDate2, sampleDate1));
        assertFalse(Timing.isValidTiming(sampleDate2, sampleDate2));
        assertTrue(Timing.isValidTiming(sampleDate2, sampleDate3));
        assertTrue(Timing.isValidTiming(sampleDate1, sampleDate4));

        assertFalse(Timing.isValidTiming(sampleDate3, sampleDate1));
        assertFalse(Timing.isValidTiming(sampleDate3, sampleDate2));
        assertFalse(Timing.isValidTiming(sampleDate3, sampleDate3));
        assertTrue(Timing.isValidTiming(sampleDate3, sampleDate4));

        assertFalse(Timing.isValidTiming(sampleDate4, sampleDate1));
        assertFalse(Timing.isValidTiming(sampleDate4, sampleDate2));
        assertFalse(Timing.isValidTiming(sampleDate4, sampleDate3));
        assertFalse(Timing.isValidTiming(sampleDate4, sampleDate4));

        assertThrows(IllegalArgumentException.class, () -> new Timing(sampleDate1, sampleDate2),
                        Timing.MESSAGE_CONSTRAINTS);
        assertThrows(NullPointerException.class, () -> Timing.isValidTiming(null, sampleDate1));
        assertThrows(NullPointerException.class, () -> Timing.isValidTiming(null, null));
        assertThrows(NullPointerException.class, () -> Timing.isValidTiming(sampleDate1, null));

    }

    @Test
    void test_timing_conflictsWith() {

        DateTime sampleDate5 = DateTime.tryParseSimpleDateFormat("10/10/20 2000");
        Timing sampleEvent5 = new Timing(sampleDate3, sampleDate5);

        assertThrows(NullPointerException.class, () -> sampleEvent1.conflictsWith(null));

        //False: Event should not conflict with itself.
        assertFalse(sampleEvent1.conflictsWith(sampleEvent1));

        //True: Event has the same timing
        assertTrue(sampleEvent1.conflictsWith(sampleEvent2));

        //True: Event1 is within the timing of event3
        assertTrue(sampleEvent1.conflictsWith(sampleEvent2));

        //False: Event1 and Event4 are back-to-back session and should not be in conflict
        assertFalse(sampleEvent1.conflictsWith(sampleEvent4));

        //False: Event1 and Event5 are back-to-back session and should not be in conflict
        assertFalse(sampleEvent1.conflictsWith(sampleEvent5));

        assertTrue(sampleEvent2.conflictsWith(sampleEvent1));
        assertFalse(sampleEvent2.conflictsWith(sampleEvent2));
        assertTrue(sampleEvent2.conflictsWith(sampleEvent3));
        assertFalse(sampleEvent2.conflictsWith(sampleEvent4));
        assertFalse(sampleEvent2.conflictsWith(sampleEvent5));

        assertTrue(sampleEvent3.conflictsWith(sampleEvent1));
        assertTrue(sampleEvent3.conflictsWith(sampleEvent2));
        assertFalse(sampleEvent3.conflictsWith(sampleEvent3));
        assertTrue(sampleEvent3.conflictsWith(sampleEvent4));
        assertTrue(sampleEvent3.conflictsWith(sampleEvent5));

        assertFalse(sampleEvent4.conflictsWith(sampleEvent1));
        assertFalse(sampleEvent4.conflictsWith(sampleEvent2));
        assertTrue(sampleEvent4.conflictsWith(sampleEvent3));
        assertFalse(sampleEvent4.conflictsWith(sampleEvent4));
        assertTrue(sampleEvent4.conflictsWith(sampleEvent5));

        assertFalse(sampleEvent5.conflictsWith(sampleEvent1));
        assertFalse(sampleEvent5.conflictsWith(sampleEvent2));
        assertTrue(sampleEvent5.conflictsWith(sampleEvent3));
        assertTrue(sampleEvent5.conflictsWith(sampleEvent4));
        assertFalse(sampleEvent5.conflictsWith(sampleEvent5));

    }

    @Test
    void test_timing_compareTo() {
        assertEquals(0, sampleEvent1.compareTo(sampleEvent1));
        assertEquals(0, sampleEvent1.compareTo(sampleEvent2));
        assertEquals(-1, sampleEvent1.compareTo(sampleEvent3));
        assertEquals(-1, sampleEvent1.compareTo(sampleEvent4));

        assertEquals(0, sampleEvent2.compareTo(sampleEvent1));
        assertEquals(0, sampleEvent2.compareTo(sampleEvent2));
        assertEquals(-1, sampleEvent2.compareTo(sampleEvent3));
        assertEquals(-1, sampleEvent2.compareTo(sampleEvent4));

        assertEquals(1, sampleEvent3.compareTo(sampleEvent1));
        assertEquals(1, sampleEvent3.compareTo(sampleEvent2));
        assertEquals(0, sampleEvent3.compareTo(sampleEvent3));
        assertEquals(-1, sampleEvent3.compareTo(sampleEvent4));

        assertEquals(1, sampleEvent4.compareTo(sampleEvent1));
        assertEquals(1, sampleEvent4.compareTo(sampleEvent2));
        assertEquals(1, sampleEvent4.compareTo(sampleEvent3));
        assertEquals(0, sampleEvent4.compareTo(sampleEvent4));

        assertThrows(NullPointerException.class, () -> sampleEvent1.compareTo(null));
    }

    @Test
    void test_timing_equals() {

        assertFalse(sampleEvent1.equals(null));
        assertFalse(sampleEvent1.equals(""));

        assertTrue(sampleEvent1.equals(sampleEvent1));

        //sample 1 and 2 should have same appointment timings
        assertTrue(sampleEvent1.equals(sampleEvent2));

        assertFalse(sampleEvent1.equals(sampleEvent3));
        assertFalse(sampleEvent1.equals(sampleEvent4));

        assertFalse(sampleEvent3.equals(sampleEvent1));
        assertFalse(sampleEvent4.equals(sampleEvent1));
        assertFalse(sampleEvent3.equals(sampleEvent4));
    }
}
