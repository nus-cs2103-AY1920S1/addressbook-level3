package dukecooks.model.health;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.health.components.Timestamp;

public class TimestampTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timestamp(null));
    }

    @Test
    public void constructor_invalidTimestamp_throwsIllegalArgumentException() {
        String invalidTimestamp = "";
        assertThrows(IllegalArgumentException.class, () -> new Timestamp(invalidTimestamp));
    }

    @Test
    public void isValidDateTime() {
        // null timestamp
        assertThrows(NullPointerException.class, () -> Timestamp.isValidDateTime(null));

        // valid timestamp format
        assertTrue(Timestamp.isValidDateTime("12/02/2019 12:00")); // exact format

        // valid date and time
        assertTrue(Timestamp.isValidDateTimePeriod("29/02/2016 12:00")); // leap date
        assertTrue(Timestamp.isValidDateTimePeriod("31/03/2019 12:00")); // month with 31 days

        // invalid timestamp format
        assertFalse(Timestamp.isValidDateTime("911")); // digits
        assertFalse(Timestamp.isValidDateTime("asasasas")); //alphabets
        assertFalse(Timestamp.isValidDateTime("9011p041")); // alphabets within digits
        assertFalse(Timestamp.isValidDateTime("12/02/2019  12:00")); // with more spaces between
        assertFalse(Timestamp.isValidDateTime("12-02-2019 12:00")); // different date format
        assertFalse(Timestamp.isValidDateTime("12/02/2019 12.00")); // different time format
        assertFalse(Timestamp.isValidDateTime("12-02-2019 12.00")); // different date and time format
        assertFalse(Timestamp.isValidDateTime("12022019 1200")); // no separators

        // invalid date and time
        assertFalse(Timestamp.isValidDateTime("29/02/2019 12:00")); // invalid leap date
        assertFalse(Timestamp.isValidDateTime("31/09/2019 12:00")); // invalid month with 31 days
        assertFalse(Timestamp.isValidDateTime("29/02/2016 25:00")); // invalid time
    }

    @Test
    public void isValidDateTimePeriod() {
        // null timestamp
        assertThrows(NullPointerException.class, () -> Timestamp.isValidDateTimePeriod(null));

        // valid timestamp
        assertTrue(Timestamp.isValidDateTimePeriod("12/02/2019 12:00")); // present date

        // invalid timestamp
        assertFalse(Timestamp.isValidDateTimePeriod("12/02/2029 12:00")); // future date
    }

    @Test
    public void testTimestampToString() {
        Timestamp timestamp = new Timestamp("10/09/2019 12:00");
        String expected = "10/09/2019 12:00";
        assertEquals(expected, timestamp.toString());
    }
}
