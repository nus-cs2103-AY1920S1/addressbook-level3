package thrift.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;


public class DateUtilsTest {

    @Test
    public void bothDatesAndCalendarNull_throwsIllegalArgumentException() {
        Date date1 = null;
        Date date2 = null;
        Calendar cal1 = null;
        Calendar cal2 = null;
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isSameDay(date1, date2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isSameDay(cal1, cal2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isAfterDay(date1, date2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isAfterDay(cal1, cal2));
    }

    @Test
    public void firstDateOrCalendarNull_secondDateOrCalendarValid_throwsIllegalArgumentException() {
        Date date1 = null;
        Date date2 = new Date();
        Calendar cal1 = null;
        Calendar cal2 = Calendar.getInstance();
        int days = 1;
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isSameDay(date1, date2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isSameDay(cal1, cal2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isToday(date1));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isToday(cal1));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isAfterDay(date1, date2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isAfterDay(cal1, cal2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isWithinDaysFuture(date1, days));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isWithinDaysFuture(cal1, days));
    }

    @Test
    public void validFirstDateOrCalendar_secondDateOrCalendarNull_throwsIllegalArgumentException() {
        Date date1 = new Date();
        Date date2 = null;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = null;
        int days = 1;
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isSameDay(date1, date2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isSameDay(cal1, cal2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isAfterDay(date1, date2));
        assertThrows(IllegalArgumentException.class, ()
            -> DateUtils.isAfterDay(cal1, cal2));
        assertDoesNotThrow(() -> DateUtils.isWithinDaysFuture(date1, days));
        assertDoesNotThrow(() -> DateUtils.isWithinDaysFuture(cal1, days));
    }

    @Test
    public void bothDatesAndCalendarValid() {
        Calendar f1 = Calendar.getInstance();
        f1.add(Calendar.DAY_OF_MONTH, 1); // 1 day in future
        Calendar f5 = Calendar.getInstance();
        f5.add(Calendar.DAY_OF_MONTH, 5); // 5 days in future
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = f1.getTime();
        Date date4 = f5.getTime();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int days0 = 0;
        int days1 = 1;
        int days5 = 5;

        // isSameDay - true
        assertTrue(DateUtils.isSameDay(date1, date2));
        assertTrue(DateUtils.isSameDay(date2, date1));
        assertTrue(DateUtils.isSameDay(cal1, cal2));
        assertTrue(DateUtils.isSameDay(cal2, cal1));

        // isSameDay - false
        assertFalse(DateUtils.isSameDay(date1, date3));
        assertFalse(DateUtils.isSameDay(date1, date4));
        assertFalse(DateUtils.isSameDay(cal1, f1));
        assertFalse(DateUtils.isSameDay(cal1, f5));

        // isToday - true
        assertTrue(DateUtils.isToday(date1));
        assertTrue(DateUtils.isToday(cal1));

        // isToday - false
        assertFalse(DateUtils.isToday(date3));
        assertFalse(DateUtils.isToday(date4));
        assertFalse(DateUtils.isToday(f1));
        assertFalse(DateUtils.isToday(f5));

        // isAfterDay - true
        assertTrue(DateUtils.isAfterDay(date3, date1));
        assertTrue(DateUtils.isAfterDay(date3, date2));
        assertTrue(DateUtils.isAfterDay(date4, date1));
        assertTrue(DateUtils.isAfterDay(date4, date2));
        assertTrue(DateUtils.isAfterDay(date4, date3));

        assertTrue(DateUtils.isAfterDay(f1, cal1));
        assertTrue(DateUtils.isAfterDay(f1, cal2));
        assertTrue(DateUtils.isAfterDay(f5, cal1));
        assertTrue(DateUtils.isAfterDay(f5, cal2));
        assertTrue(DateUtils.isAfterDay(f5, f1));

        // isAfterDay - false
        assertFalse(DateUtils.isAfterDay(date1, date3));
        assertFalse(DateUtils.isAfterDay(date2, date3));
        assertFalse(DateUtils.isAfterDay(date1, date4));
        assertFalse(DateUtils.isAfterDay(date2, date4));
        assertFalse(DateUtils.isAfterDay(date3, date4));

        assertFalse(DateUtils.isAfterDay(cal1, f1));
        assertFalse(DateUtils.isAfterDay(cal2, f1));
        assertFalse(DateUtils.isAfterDay(cal1, f5));
        assertFalse(DateUtils.isAfterDay(cal2, f5));
        assertFalse(DateUtils.isAfterDay(f1, f5));

        assertFalse(DateUtils.isAfterDay(date1, date2));
        assertFalse(DateUtils.isAfterDay(cal1, cal2));

        // isWithinDaysFuture - true
        assertTrue(DateUtils.isWithinDaysFuture(date3, days1));
        assertTrue(DateUtils.isWithinDaysFuture(date3, days5));
        assertTrue(DateUtils.isWithinDaysFuture(date4, days5));
        assertTrue(DateUtils.isWithinDaysFuture(f1, days1));
        assertTrue(DateUtils.isWithinDaysFuture(f1, days5));
        assertTrue(DateUtils.isWithinDaysFuture(f5, days5));

        // isWithinDaysFuture - false
        assertFalse(DateUtils.isWithinDaysFuture(date1, days0));
        assertFalse(DateUtils.isWithinDaysFuture(date2, days0));
        assertFalse(DateUtils.isWithinDaysFuture(cal1, days0));
        assertFalse(DateUtils.isWithinDaysFuture(cal2, days0));
        assertFalse(DateUtils.isWithinDaysFuture(date3, days0));
        assertFalse(DateUtils.isWithinDaysFuture(date4, days0));
        assertFalse(DateUtils.isWithinDaysFuture(date4, days1));
        assertFalse(DateUtils.isWithinDaysFuture(f1, days0));
        assertFalse(DateUtils.isWithinDaysFuture(f5, days0));
        assertFalse(DateUtils.isWithinDaysFuture(f5, days1));
    }
}
