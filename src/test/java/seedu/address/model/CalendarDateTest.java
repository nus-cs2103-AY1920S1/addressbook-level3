package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class CalendarDateTest {

    private final Calendar calendar = new Calendar.Builder()
            .setDate(2020, 11, 11).setTimeOfDay(11, 11, 0).build();
    private final CalendarDate calendarDate = new CalendarDate(calendar);

    @Test
    public void constructor() {
        assertEquals(calendar, calendarDate.getCalendar());
    }

    @Test
    public void setCalendar_nullCalendar_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> calendarDate.setCalendar(null));
    }

    @Test
    public void setCalendar_validCalendar_replacesData() {
        Calendar newCalendar = Calendar.getInstance();
        calendarDate.setCalendar(newCalendar);
        assertEquals(newCalendar, calendarDate.getCalendar());
    }

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(calendarDate.equals(calendarDate));

        // null -> returns false
        assertFalse(calendarDate.equals(null));

        // different types -> returns false
        assertFalse(calendarDate.equals(5));

        // same calendar -> returns true
        CalendarDate newCalendarDate = new CalendarDate(calendar);
        assertTrue(calendarDate.equals(newCalendarDate));

        // different calendar -> returns false
        newCalendarDate = new CalendarDate(Calendar.getInstance());
        assertFalse(calendarDate.equals(newCalendarDate));

    }
}
