package seedu.address.model.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class DateTimeTest {
    private LocalDate validDate = LocalDate.of(2020, 1, 20);
    private LocalTime validTime = LocalTime.of(12, 30);
    private DateTime validDateTime = new DateTime(validDate, validTime);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null, null));
        assertThrows(NullPointerException.class, () -> new DateTime(null, validTime));
        assertThrows(NullPointerException.class, () -> new DateTime(validDate, null));
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
        assertEquals("2020 Jan 20 12:30", validDateTime.toString());
    }
}
