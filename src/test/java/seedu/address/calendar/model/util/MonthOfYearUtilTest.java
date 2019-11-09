package seedu.address.calendar.model.util;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.MonthOfYear;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonthOfYearUtilTest {

    @Test
    public void convertJavaMonth_invalidJavaMonth_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertJavaMonth(Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertJavaMonth(Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertJavaMonth(-1));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertJavaMonth(12));
    }

    @Test
    public void convertJavaMonth() {
        assertEquals(MonthOfYear.JANUARY, MonthOfYearUtil.convertJavaMonth(0));
        assertEquals(MonthOfYear.FEBRUARY, MonthOfYearUtil.convertJavaMonth(1));
        assertEquals(MonthOfYear.MARCH, MonthOfYearUtil.convertJavaMonth(2));
        assertEquals(MonthOfYear.APRIL, MonthOfYearUtil.convertJavaMonth(3));
        assertEquals(MonthOfYear.MAY, MonthOfYearUtil.convertJavaMonth(4));
        assertEquals(MonthOfYear.JUNE, MonthOfYearUtil.convertJavaMonth(5));
        assertEquals(MonthOfYear.JULY, MonthOfYearUtil.convertJavaMonth(6));
        assertEquals(MonthOfYear.AUGUST, MonthOfYearUtil.convertJavaMonth(7));
        assertEquals(MonthOfYear.SEPTEMBER, MonthOfYearUtil.convertJavaMonth(8));
        assertEquals(MonthOfYear.OCTOBER, MonthOfYearUtil.convertJavaMonth(9));
        assertEquals(MonthOfYear.NOVEMBER, MonthOfYearUtil.convertJavaMonth(10));
        assertEquals(MonthOfYear.DECEMBER, MonthOfYearUtil.convertJavaMonth(11));
    }

    @Test
    public void isValidZeroBasedMonthNum() {
        IntStream.rangeClosed(0, 11)
                .forEach(i -> assertTrue(MonthOfYearUtil.isValidZeroBasedMonthNum(i)));
        assertFalse(MonthOfYearUtil.isValidZeroBasedMonthNum(-1));
        assertFalse(MonthOfYearUtil.isValidZeroBasedMonthNum(12));
        assertFalse(MonthOfYearUtil.isValidZeroBasedMonthNum(Integer.MAX_VALUE));
        assertFalse(MonthOfYearUtil.isValidZeroBasedMonthNum(Integer.MIN_VALUE));
    }

    @Test
    public void isValidMonthStr() {
        // complete month string
        assertTrue(MonthOfYearUtil.isValidMonthStr("January"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("February"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("March"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("April"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("May"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("June"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("July"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("August"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("September"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("October"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("November"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("December"));

        // incomplete but sufficient number of characters string
        assertTrue(MonthOfYearUtil.isValidMonthStr("Jan"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("Febr"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("Jul"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("Octob"));

        // with extra spaces
        assertTrue(MonthOfYearUtil.isValidMonthStr(" Jan"));
        assertTrue(MonthOfYearUtil.isValidMonthStr("May "));

        // insufficient number of characters
        assertFalse(MonthOfYearUtil.isValidMonthStr("Oc"));
        assertFalse(MonthOfYearUtil.isValidMonthStr(""));

        // invalid strings
        assertFalse(MonthOfYearUtil.isValidMonthStr("Oktober"));
        assertFalse(MonthOfYearUtil.isValidMonthStr("Octopus"));
        assertFalse(MonthOfYearUtil.isValidMonthStr("October12"));
    }

    @Test
    public void convertStrToMonth_invalidMonthStr_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertStrToMonth("Oc"));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertStrToMonth(""));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertStrToMonth("October1"));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertStrToMonth("Ta da 12"));
        assertThrows(IllegalArgumentException.class, () -> MonthOfYearUtil.convertStrToMonth("ber"));
    }

    @Test
    public void convertStrToMonth() {
        assertEquals(MonthOfYear.JANUARY, MonthOfYearUtil.convertStrToMonth("January"));
        assertEquals(MonthOfYear.FEBRUARY, MonthOfYearUtil.convertStrToMonth("Febr"));
        assertEquals(MonthOfYear.MARCH, MonthOfYearUtil.convertStrToMonth("March"));
        assertEquals(MonthOfYear.APRIL, MonthOfYearUtil.convertStrToMonth("Apri"));
        assertEquals(MonthOfYear.MAY, MonthOfYearUtil.convertStrToMonth("May"));
        assertEquals(MonthOfYear.JUNE, MonthOfYearUtil.convertStrToMonth("June"));
        assertEquals(MonthOfYear.JULY, MonthOfYearUtil.convertStrToMonth("Jul"));
        assertEquals(MonthOfYear.AUGUST, MonthOfYearUtil.convertStrToMonth("Augus"));
        assertEquals(MonthOfYear.SEPTEMBER, MonthOfYearUtil.convertStrToMonth("Septembe"));
        assertEquals(MonthOfYear.OCTOBER, MonthOfYearUtil.convertStrToMonth("October"));
        assertEquals(MonthOfYear.NOVEMBER, MonthOfYearUtil.convertStrToMonth("November"));
        assertEquals(MonthOfYear.DECEMBER, MonthOfYearUtil.convertStrToMonth("December"));
    }
}
