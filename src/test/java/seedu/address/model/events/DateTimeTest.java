package seedu.address.model.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeTest {

    private DateTime sampleDate1;
    private DateTime sampleDate2;
    private DateTime sampleDate3;

    @BeforeEach
    void setup() {
        sampleDate1 = DateTime.tryParseSimpleDateFormat("10/10/20 0100");
        sampleDate2 = DateTime.tryParseSimpleDateFormat("10/10/20 0100");
        sampleDate3 = DateTime.tryParseSimpleDateFormat("10/10/20 1800");
    }

    @Test
    void test_getTime() {
        assertTrue(sampleDate1.getTime().equals(sampleDate2.getTime()));
        assertFalse(sampleDate1.getTime().equals(sampleDate3.getTime()));
        assertFalse(sampleDate2.getTime().equals(sampleDate3.getTime()));
    }

    @Test
    void test_tryParseSimpleDateFormat() {
        assertNotNull(sampleDate1);
        assertNotNull(sampleDate2);
        assertNotNull(sampleDate3);

        assertTrue(DateTime.tryParseSimpleDateFormat("10/10/2020 0100").equals(sampleDate1));

        assertNull(DateTime.tryParseSimpleDateFormat("10/10/20 0160"));
        assertNull(DateTime.tryParseSimpleDateFormat("101020 0100"));
        assertNull(DateTime.tryParseSimpleDateFormat("  "));

        assertThrows(NullPointerException.class, () -> DateTime.tryParseSimpleDateFormat(null));
    }

    @Test
    void test_before() {
        assertFalse(sampleDate1.before(sampleDate1));
        assertFalse(sampleDate1.before(sampleDate2));
        assertTrue(sampleDate1.before(sampleDate3));

        assertFalse(sampleDate2.before(sampleDate1));
        assertFalse(sampleDate2.before(sampleDate2));
        assertTrue(sampleDate2.before(sampleDate3));

        assertFalse(sampleDate3.before(sampleDate1));
        assertFalse(sampleDate3.before(sampleDate2));
        assertFalse(sampleDate3.before(sampleDate3));
    }

    @Test
    void test_beforeOrEqual() {
        assertTrue(sampleDate1.beforeOrEqual(sampleDate1));
        assertTrue(sampleDate1.beforeOrEqual(sampleDate2));
        assertTrue(sampleDate1.beforeOrEqual(sampleDate3));

        assertTrue(sampleDate2.beforeOrEqual(sampleDate1));
        assertTrue(sampleDate2.beforeOrEqual(sampleDate2));
        assertTrue(sampleDate2.beforeOrEqual(sampleDate3));

        assertFalse(sampleDate3.beforeOrEqual(sampleDate1));
        assertFalse(sampleDate3.beforeOrEqual(sampleDate2));
        assertTrue(sampleDate3.beforeOrEqual(sampleDate3));
    }

    @Test
    void test_timing_compareTo() {
        assertEquals(0, sampleDate1.compareTo(sampleDate1));
        assertEquals(0, sampleDate1.compareTo(sampleDate2));
        assertEquals(-1, sampleDate1.compareTo(sampleDate3));

        assertEquals(0, sampleDate2.compareTo(sampleDate1));
        assertEquals(0, sampleDate2.compareTo(sampleDate2));
        assertEquals(-1, sampleDate2.compareTo(sampleDate3));

        assertEquals(1, sampleDate3.compareTo(sampleDate1));
        assertEquals(1, sampleDate3.compareTo(sampleDate2));
        assertEquals(0, sampleDate3.compareTo(sampleDate3));

        assertThrows(NullPointerException.class, () -> sampleDate1.compareTo(null));
    }
    @Test
    void test_testEquals() {
        assertTrue(sampleDate1.equals(sampleDate1));
        assertTrue(sampleDate1.equals(sampleDate2));
        assertFalse(sampleDate1.equals(sampleDate3));

        assertFalse(sampleDate1.equals("11/11"));
        assertFalse(sampleDate1.equals("   "));
        assertFalse(sampleDate1.equals(null));
        assertFalse(sampleDate1.equals(new Date()));

        assertTrue(sampleDate2.equals(sampleDate1));
        assertTrue(sampleDate2.equals(sampleDate2));
        assertFalse(sampleDate2.equals(sampleDate3));

        assertFalse(sampleDate3.equals(sampleDate1));
        assertFalse(sampleDate3.equals(sampleDate2));
        assertTrue(sampleDate3.equals(sampleDate3));

        assertTrue(sampleDate1.equals(DateTime.tryParseSimpleDateFormat(sampleDate1.toString())));
        assertTrue(sampleDate2.equals(DateTime.tryParseSimpleDateFormat(sampleDate2.toString())));
        assertTrue(sampleDate3.equals(DateTime.tryParseSimpleDateFormat(sampleDate3.toString())));
    }

}
