package io.xpire.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.xpire.testutil.Assert;

public class DateUtilTest {

    @Test
    public void convertDateToString_nullDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.convertDateToString(null));
    }

    @Test
    public void convertDateToString_validDate_dateInString() {
        // typical date
        assertEquals("25/12/2019", DateUtil.convertDateToString(LocalDate.of(2019, 12, 25)));

        // date with single digit month and day
        assertEquals("1/9/2019", DateUtil.convertDateToString(LocalDate.of(2019, 9, 1)));

        // leap year
        assertEquals("29/2/2020", DateUtil.convertDateToString(LocalDate.of(2020, 2, 29)));
    }

    @Test
    public void convertStringToDate_nullString_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.convertStringToDate(null));
    }

    @Test
    public void convertStringToDate_invalidString_null() {
        // empty string
        assertNull(DateUtil.convertStringToDate(""));

        // incorrect format
        assertNull(DateUtil.convertStringToDate("1"));

        // contains letters
        assertNull(DateUtil.convertStringToDate("1/12/a2019"));

        // invalid days
        assertNull(DateUtil.convertStringToDate("33/5/2019"));
        assertNull(DateUtil.convertStringToDate("0/5/2019"));
        assertNull(DateUtil.convertStringToDate("-3/5/2019"));

        // invalid months
        assertNull(DateUtil.convertStringToDate("25/13/2019"));
        assertNull(DateUtil.convertStringToDate("25/0/2019"));
        assertNull(DateUtil.convertStringToDate("25/-2/2019"));

        // invalid years
        assertNull(DateUtil.convertStringToDate("25/2/002019"));
        assertNull(DateUtil.convertStringToDate("25/2/10000"));
        assertNull(DateUtil.convertStringToDate("25/2/0"));
        assertNull(DateUtil.convertStringToDate("25/2/-2"));

        //******************* invalid dates edge cases *******************//
        // non-leap year
        assertNull(DateUtil.convertStringToDate("29/2/2019"));

        // invalid days for february
        assertNull(DateUtil.convertStringToDate("30/2/2019"));
        assertNull(DateUtil.convertStringToDate("31/2/2019"));

        // non 31-days months
        assertNull(DateUtil.convertStringToDate("31/4/2019"));
        assertNull(DateUtil.convertStringToDate("31/6/2019"));
        assertNull(DateUtil.convertStringToDate("31/9/2019"));
        assertNull(DateUtil.convertStringToDate("31/11/2019"));
    }

    @Test
    public void convertStringToDate_validString_localDate() {
        // typical date (without zero padded)
        assertEquals(LocalDate.of(2019, 8, 1), DateUtil.convertStringToDate("1/8/2019"));
        assertEquals(LocalDate.of(2019, 12, 25), DateUtil.convertStringToDate("25/12/2019"));

        // typical date (with zero padded)
        assertEquals(LocalDate.of(2019, 8, 1), DateUtil.convertStringToDate("01/08/2019"));

        // typical date (with excessive zero padded)
        assertEquals(LocalDate.of(2019, 8, 1), DateUtil.convertStringToDate("0001/0008/2019"));
        assertEquals(LocalDate.of(2019, 12, 25), DateUtil.convertStringToDate("00025/00012/2019"));

        // leap year
        assertEquals(LocalDate.of(2020, 2, 29), DateUtil.convertStringToDate("29/2/2020"));

        // 31-days months
        assertEquals(LocalDate.of(2019, 1, 31), DateUtil.convertStringToDate("31/1/2019"));
        assertEquals(LocalDate.of(2019, 3, 31), DateUtil.convertStringToDate("31/3/2019"));
        assertEquals(LocalDate.of(2019, 5, 31), DateUtil.convertStringToDate("31/5/2019"));
        assertEquals(LocalDate.of(2019, 7, 31), DateUtil.convertStringToDate("31/7/2019"));
        assertEquals(LocalDate.of(2019, 8, 31), DateUtil.convertStringToDate("31/8/2019"));
        assertEquals(LocalDate.of(2019, 10, 31), DateUtil.convertStringToDate("31/10/2019"));
        assertEquals(LocalDate.of(2019, 12, 31), DateUtil.convertStringToDate("31/12/2019"));
    }

    @Test
    public void isWithinRange_nullDates_throwsNullPointerException() {
        // both dates are null
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isWithinRange(1, null, null));
        // earlier date is null
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isWithinRange(1, LocalDate.now(), null));
        // later date is null
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isWithinRange(1, null, LocalDate.now()));
    }

    @Test
    public void isWithinRange_negativeDays_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> DateUtil
                .isWithinRange(-1, LocalDate.now(), LocalDate.now()));
    }

    @Test
    public void isWithinRange_earlierDateIsAfterLaterDate_true() {
        LocalDate earlierDate = LocalDate.now();
        LocalDate laterDate = earlierDate.minusDays(10);
        assertTrue(DateUtil.isWithinRange(1, earlierDate, laterDate));
    }

    @Test
    public void isWithinRange_daysWithinRange_true() {
        LocalDate earlierDate = LocalDate.now();
        LocalDate laterDate = earlierDate.plusDays(5);

        // just nice within range
        assertTrue(DateUtil.isWithinRange(5, earlierDate, laterDate));
        // within range
        assertTrue(DateUtil.isWithinRange(6, earlierDate, laterDate));
    }

    @Test
    public void isWithinRange_daysNotWithinRange_false() {
        LocalDate earlierDate = LocalDate.now();
        LocalDate laterDate = earlierDate.plusDays(5);

        // just nice not within range
        assertFalse(DateUtil.isWithinRange(4, earlierDate, laterDate));
        // not within range
        assertFalse(DateUtil.isWithinRange(2, earlierDate, laterDate));
    }

    @Test
    public void getOffsetDays_nullDates_throwsNullPointerException() {
        // both dates are null
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.getOffsetDays(null, null));
        // earlierDate is null
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.getOffsetDays(LocalDate.now(), null));
        // laterDate is null
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.getOffsetDays(null, LocalDate.now()));
    }

    @Test
    public void getOffsetDays_sameDate_zero() {
        assertEquals(0, DateUtil.getOffsetDays(LocalDate.now(), LocalDate.now()));
    }

    @Test
    public void getOffsetDays_earlierDateIsAfterLaterDate_negativeNumber() {
        LocalDate earlierDate = LocalDate.now();
        LocalDate laterDate = earlierDate.minusDays(5);
        assertEquals(-5, DateUtil.getOffsetDays(earlierDate, laterDate));
    }

    @Test
    public void getOffsetDays_earlierDateIsBeforeLaterDate_positiveNumber() {
        LocalDate earlierDate = LocalDate.now();
        LocalDate laterDate = earlierDate.plusDays(5);
        assertEquals(5, DateUtil.getOffsetDays(earlierDate, laterDate));
    }

    @Test
    public void getPreviousDate_nullDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.getPreviousDate(null, 1));
    }

    @Test
    public void getPreviousDate_negativeOffsetDays_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> DateUtil.getPreviousDate(LocalDate.now(), -1));
    }

    @Test
    public void getPreviousDate_zeroOffsetDay_sameDate() {
        assertEquals(LocalDate.now(), DateUtil.getPreviousDate(LocalDate.now(), 0));
    }

    @Test
    public void getPreviousDate_positiveOffsetDays_earlierDate() {
        assertEquals(LocalDate.now().minusDays(5), DateUtil.getPreviousDate(LocalDate.now(), 5));
    }

    @Test
    public void getCurrentDate() {
        assertEquals(LocalDate.now(), DateUtil.getCurrentDate());
    }
}
