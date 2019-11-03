package seedu.sugarmummy.model.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.model.time.DateTime;

class DateTimeTest {
    private LocalDate validDate = LocalDate.of(2020, 1, 20);
    private LocalTime validTime = LocalTime.of(12, 30);
    private DateTime validDateTime = new DateTime(validDate, validTime);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null, null));
        assertThrows(NullPointerException.class, () -> new DateTime(null, validTime));
        assertThrows(NullPointerException.class, () -> new DateTime(validDate, null));
        assertThrows(NullPointerException.class, () -> new DateTime((String) null));
    }

    @Test
    public void isValidDateTime_false() {
        assertFalse(DateTime.isValidDateTime("2019-02-30 12:30"));
        assertFalse(DateTime.isValidDateTime("2019-02-20 24:30"));
        assertFalse(DateTime.isValidDateTime("2019-02-20 12:70"));
        assertFalse(DateTime.isValidDateTime("2019-13-20 30:02"));
        assertFalse(DateTime.isValidDateTime("9999-02-30 20:20"));
        assertFalse(DateTime.isValidDateTime("0000-2-2 12:20"));
        assertFalse(DateTime.isValidDateTime("222-2-12 11:20"));
        assertFalse(DateTime.isValidDateTime("2222-12-2 1:20"));
        assertFalse(DateTime.isValidDateTime("2222-12-01 :"));
    }

    @Test
    public void isValidDateTime_true() {
        assertTrue(DateTime.isValidDateTime("2019-02-28 12:59"));
        assertTrue(DateTime.isValidDateTime("0022-12-31 00:20"));
    }

    @Test
    public void constructor_string() {
        assertEquals(validDateTime, new DateTime("2020-01-20 12:30"));
    }

    @Test
    public void getDate() {
        assertEquals(validDate, validDateTime.getDate());
    }

    @Test
    public void getTime() {
        assertEquals(validTime, validDateTime.getTime());
    }

    @Test
    void getDayOfWeek() {
        assertEquals(DayOfWeek.MONDAY, validDateTime.getDayOfWeek());
    }

    @Test
    void getDayOfWeekString() {
        assertEquals("Monday", validDateTime.getDayOfWeekString());
    }

    @Test
    void testToString() {
        assertEquals("2020-01-20 12:30", validDateTime.toString());
    }
}
