package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CalendarDateTest {

    @Test
    void getDateTest() {
        assertDoesNotThrow(() -> {
            CalendarDate dayMonthYear = CalendarDate.fromDayMonthYearString("11/11/2019");
            assertEquals(dayMonthYear.getDay(), 11);
            assertEquals(dayMonthYear.getMonth(), 11);
            assertEquals(dayMonthYear.getYear(), 2019);
            assertEquals(dayMonthYear.getEnglishMonth(), "November");
            assertEquals(dayMonthYear.getWeekIndex(), 1);
            assertEquals(dayMonthYear.lengthOfMonth(), 30);

            CalendarDate monthYear = CalendarDate.fromMonthYearString("11/2019");
            assertEquals(monthYear.getMonth(), 11);
            assertEquals(monthYear.getYear(), 2019);
            assertEquals(monthYear.getEnglishMonth(), "November");
            assertEquals(monthYear.lengthOfMonth(), 30);
        });
    }

    @Test
    void sameDateTest() {
        assertDoesNotThrow(() -> {
            CalendarDate dayMonthYear = CalendarDate.fromDayMonthYearString("11/11/2019");
            dayMonthYear.sameDate(11, 11, 2019);
            dayMonthYear.sameMonthYear(11, 2019);

            CalendarDate monthYear = CalendarDate.fromMonthYearString("10/2019");
            monthYear.sameDate(30, 11, 2019);
            monthYear.sameMonthYear(11, 2019);
        });
    }

    @Test
    void changeDateTest() {
        assertDoesNotThrow(() -> {
            CalendarDate dayMonthYear = CalendarDate.fromDayMonthYearString("11/11/2019");
            dayMonthYear = dayMonthYear.previousDay();
            assertTrue(dayMonthYear.sameDate(10, 11, 2019));
            dayMonthYear = dayMonthYear.previousDays(15);
            assertTrue(dayMonthYear.sameDate(26, 10, 2019));

            dayMonthYear = dayMonthYear.nextDay();
            assertTrue(dayMonthYear.sameDate(27, 10, 2019));

            dayMonthYear = dayMonthYear.firstDayOfTheMonth();
            assertTrue(dayMonthYear.sameDate(1, 10, 2019));

            // TODO: Previous Month
            /*
            CalendarDate monthYear = CalendarDate.fromMonthYearString("11/2019");
            monthYear = monthYear.previousDay();
            assertTrue(monthYear.sameDate(31, 10, 2019));
            monthYear = monthYear.previousDays(3);
            assertTrue(monthYear.sameDate(28, 10, 2019));

            monthYear = monthYear.nextDay();
            assertTrue(monthYear.sameDate(28, 10, 2019));

            monthYear = monthYear.firstDayOfTheMonth();
            assertTrue(monthYear.sameDate(1, 10, 2019));
             */
        });
    }

    @Test
    void equalityTest() {
        assertDoesNotThrow(() -> {
            CalendarDate dayMonthYear = CalendarDate.fromDayMonthYearString("01/11/2019");
            CalendarDate monthYear = CalendarDate.fromMonthYearString("11/2019");
            assertEquals(dayMonthYear, monthYear);

            assertEquals(dayMonthYear.nextDay(), monthYear.nextDay());
            assertEquals(dayMonthYear.previousDays(100), monthYear.previousDays(100));
        });
    }

    @Test
    void toStringTest() {
        assertDoesNotThrow(() -> {
            CalendarDate dayMonthYear = CalendarDate.fromDayMonthYearString("11/11/2019");
            CalendarDate monthYear = CalendarDate.fromMonthYearString("10/2019");
            assertEquals(dayMonthYear.toString(), "11/11/2019");
            assertEquals(monthYear.toString(), "1/10/2019");
        });
    }

}
