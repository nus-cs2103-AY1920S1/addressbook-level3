package seedu.address.calendar.model.util;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateUtilTest {

    @Test
    public void daysBetween_endDateBeforeStartDate_throwsIllegalArgumentException() {
        Date dateBefore = new Date(new Day(DayOfWeek.WED, 1, MonthOfYear.NOVEMBER, new Year(1989)),
                MonthOfYear.NOVEMBER, new Year(1989));
        Date dateAfter = new Date(new Day(DayOfWeek.THU, 2, MonthOfYear.NOVEMBER, new Year(1989)),
                MonthOfYear.NOVEMBER, new Year(1989));
        assertThrows(IllegalArgumentException.class, () -> DateUtil.daysBetween(dateAfter, dateBefore));
    }

    @Test
    public void daysBetween() {
        Date dateBefore = new Date(new Day(DayOfWeek.WED, 1, MonthOfYear.NOVEMBER, new Year(1989)),
                MonthOfYear.NOVEMBER, new Year(1989));
        Date dateAfter = new Date(new Day(DayOfWeek.THU, 2, MonthOfYear.NOVEMBER, new Year(1989)),
                MonthOfYear.NOVEMBER, new Year(1989));
        Date dateBefore2 = new Date(new Day(DayOfWeek.MON, 6, MonthOfYear.FEBRUARY, new Year(1995)),
                MonthOfYear.FEBRUARY, new Year(1995));
        Date dateAfter2 = new Date(new Day(DayOfWeek.WED, 1, MonthOfYear.MARCH, new Year(1995)),
                MonthOfYear.MARCH, new Year(1995));
        Date dateBefore3 = new Date(new Day(DayOfWeek.THU, 31, MonthOfYear.DECEMBER, new Year(1998)),
                MonthOfYear.DECEMBER, new Year(1998));
        Date dateAfter3 = new Date(new Day(DayOfWeek.FRI, 1, MonthOfYear.JANUARY, new Year(1999)),
                MonthOfYear.JANUARY, new Year(1999));

        assertEquals(0, DateUtil.daysBetween(dateBefore, dateBefore));
        assertEquals(1, DateUtil.daysBetween(dateBefore, dateAfter));
        assertEquals(23, DateUtil.daysBetween(dateBefore2, dateAfter2));
        assertEquals(1, DateUtil.daysBetween(dateBefore3, dateAfter3));
    }

    @Test
    public void getFirstDateInMonth() {
        Year year = new Year(2001);

        MonthOfYear jan = MonthOfYear.JANUARY;
        Date firstDayOfMonthExpected = new Date(new Day(DayOfWeek.MON, 1, MonthOfYear.JANUARY, new Year(2001)),
                MonthOfYear.JANUARY, new Year(2001));

        MonthOfYear apr = MonthOfYear.APRIL;
        Date dateInAprExpected = new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.APRIL, new Year(2001)),
                MonthOfYear.APRIL, new Year(2001));

        MonthOfYear may = MonthOfYear.MAY;
        Date dateInMayExpected = new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.MAY, new Year(2001)),
                MonthOfYear.MAY, new Year(2001));

        MonthOfYear feb = MonthOfYear.FEBRUARY;
        Date dateInFebExpected = new Date(new Day(DayOfWeek.THU, 1, MonthOfYear.FEBRUARY, new Year(2001)),
                MonthOfYear.FEBRUARY, new Year(2001));

        assertEquals(firstDayOfMonthExpected, DateUtil.getFirstDateInMonth(jan, year));
        assertEquals(dateInAprExpected, DateUtil.getFirstDateInMonth(apr, year));
        assertEquals(dateInMayExpected, DateUtil.getFirstDateInMonth(may, year));
        assertEquals(dateInFebExpected, DateUtil.getFirstDateInMonth(feb, year));
    }

    @Test
    public void getLastDateInMonth() {
        Year year = new Year(2021);

        MonthOfYear jan = MonthOfYear.JANUARY;
        Date firstDayOfMonthExpected = new Date(new Day(DayOfWeek.SUN, 31, MonthOfYear.JANUARY, new Year(2021)),
                MonthOfYear.JANUARY, new Year(2021));

        MonthOfYear apr = MonthOfYear.APRIL;
        Date dateInAprExpected = new Date(new Day(DayOfWeek.FRI, 30, MonthOfYear.APRIL, new Year(2021)),
                MonthOfYear.APRIL, new Year(2021));

        MonthOfYear may = MonthOfYear.MAY;
        Date dateInMayExpected = new Date(new Day(DayOfWeek.MON, 31, MonthOfYear.MAY, new Year(2021)),
                MonthOfYear.MAY, new Year(2021));

        MonthOfYear feb = MonthOfYear.FEBRUARY;
        Date dateInFebExpected = new Date(new Day(DayOfWeek.SUN, 28, MonthOfYear.FEBRUARY, new Year(2021)),
                MonthOfYear.FEBRUARY, new Year(2021));

        assertEquals(firstDayOfMonthExpected, DateUtil.getLastDateInMonth(jan, year));
        assertEquals(dateInAprExpected, DateUtil.getLastDateInMonth(apr, year));
        assertEquals(dateInMayExpected, DateUtil.getLastDateInMonth(may, year));
        assertEquals(dateInFebExpected, DateUtil.getLastDateInMonth(feb, year));

    }

    @Test
    public void getFirstDateInSameMonth() {
        Date firstDayOfMonth = new Date(new Day(DayOfWeek.MON, 1, MonthOfYear.JANUARY, new Year(2001)),
                MonthOfYear.JANUARY, new Year(2001));
        Date firstDayOfMonthExpected = new Date(new Day(DayOfWeek.MON, 1, MonthOfYear.JANUARY, new Year(2001)),
                MonthOfYear.JANUARY, new Year(2001));
        Date dateInApr = new Date(new Day(DayOfWeek.TUE, 24, MonthOfYear.APRIL, new Year(2001)),
                MonthOfYear.APRIL, new Year(2001));
        Date dateInAprExpected = new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.APRIL, new Year(2001)),
                MonthOfYear.APRIL, new Year(2001));
        Date dateInMay = new Date(new Day(DayOfWeek.THU, 31, MonthOfYear.MAY, new Year(2001)),
                MonthOfYear.MAY, new Year(2001));
        Date dateInMayExpected = new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.MAY, new Year(2001)),
                MonthOfYear.MAY, new Year(2001));
        Date dateInFeb = new Date(new Day(DayOfWeek.WED, 28, MonthOfYear.FEBRUARY, new Year(2001)),
                MonthOfYear.FEBRUARY, new Year(2001));
        Date dateInFebExpected = new Date(new Day(DayOfWeek.THU, 1, MonthOfYear.FEBRUARY, new Year(2001)),
                MonthOfYear.FEBRUARY, new Year(2001));

        assertEquals(firstDayOfMonthExpected, DateUtil.getFirstDateInSameMonth(firstDayOfMonth));
        assertEquals(dateInAprExpected, DateUtil.getFirstDateInSameMonth(dateInApr));
        assertEquals(dateInMayExpected, DateUtil.getFirstDateInSameMonth(dateInMay));
        assertEquals(dateInFebExpected, DateUtil.getFirstDateInSameMonth(dateInFeb));
    }

    @Test
    public void getLastDateInSameMonth() {
        Date lastDayOfMonth = new Date(new Day(DayOfWeek.THU, 31, MonthOfYear.DECEMBER, new Year(2009)),
                MonthOfYear.DECEMBER, new Year(2009));
        Date lastDayOfMonthExpected = new Date(new Day(DayOfWeek.THU, 31, MonthOfYear.DECEMBER, new Year(2009)),
                MonthOfYear.DECEMBER, new Year(2009));
        Date dateInApr = new Date(new Day(DayOfWeek.TUE, 21, MonthOfYear.APRIL, new Year(2009)),
                MonthOfYear.APRIL, new Year(2009));
        Date dateInAprExpected = new Date(new Day(DayOfWeek.THU, 30, MonthOfYear.APRIL, new Year(2009)),
                MonthOfYear.APRIL, new Year(2009));
        Date dateInMay = new Date(new Day(DayOfWeek.FRI, 1, MonthOfYear.MAY, new Year(2009)),
                MonthOfYear.MAY, new Year(2009));
        Date dateInMayExpected = new Date(new Day(DayOfWeek.SUN, 31, MonthOfYear.MAY, new Year(2009)),
                MonthOfYear.MAY, new Year(2009));
        Date dateInFeb = new Date(new Day(DayOfWeek.WED, 1, MonthOfYear.FEBRUARY, new Year(2012)),
                MonthOfYear.FEBRUARY, new Year(2012));
        Date dateInFebExpected = new Date(new Day(DayOfWeek.WED, 29, MonthOfYear.FEBRUARY, new Year(2012)),
                MonthOfYear.FEBRUARY, new Year(2012));

        assertEquals(lastDayOfMonthExpected, DateUtil.getLastDateInSameMonth(lastDayOfMonth));
        assertEquals(dateInAprExpected, DateUtil.getLastDateInSameMonth(dateInApr));
        assertEquals(dateInMayExpected, DateUtil.getLastDateInSameMonth(dateInMay));
        assertEquals(dateInFebExpected, DateUtil.getLastDateInSameMonth(dateInFeb));
    }
}
