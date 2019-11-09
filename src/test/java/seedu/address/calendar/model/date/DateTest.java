package seedu.address.calendar.model.date;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // null day of week
        assertThrows(NullPointerException.class, () -> new Date(null, MonthOfYear.DECEMBER, new Year(2019)));
        // null month of year
        assertThrows(NullPointerException.class, () -> new Date(new Day(DayOfWeek.WED, 6,
                MonthOfYear.NOVEMBER, new Year(2019)), null, new Year(2019)));
        // null year
        assertThrows(NullPointerException.class, () -> new Date(new Day(DayOfWeek.WED, 6,
                MonthOfYear.NOVEMBER, new Year(2019)), MonthOfYear.NOVEMBER, null));
    }

    @Test
    public void getInstanceFromString_invalidString_throwsIllegalValueException() {
        // missing DDD
        String missingDDD = ", 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingDDD));
        // only DD
        String onlyDD = "Th, 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(onlyDD));
        // with DDDD
        String DDDD = "Thur, 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(DDDD));

        // missing comma
        String missingComma = "Thu 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingComma));
        // missing spacing
        String missingSpacing = "Thu,31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingSpacing));
        // missing dd
        String missingDd = "Thu, October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingDd));
        // ddd
        String Ddd = "Tue, 001 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(Ddd));

        // missing MMM
        String missingMMM = "Thu, 31 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingMMM));
        // only MM
        String onlyMM = "Thu, 31 Oc 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(onlyMM));

        // missing yyyy
        String missingYyyy = "Thu, 31 October ";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingYyyy));
        // only yyy
        String onlyYyy = "Thu, 31 October 201";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(onlyYyy));
        // extra y
        String extraY = "Thu, 31 October 20190";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(extraY));

        // random string
        String randomString = "I love CS2103";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(randomString));

        // illegal day of week
        String illegalDayOfWeek = "Foo, 31 October 2019";
        String illegalDayOfWeek1 = "foo, 31 October 2019";
        String illegalDayOfWeekAndMissingInfo = "Foo, October 2019";
        String illegalDayOfWeekLong = "Thursday, 31 October 2019";
        String illegalDayOfWeekNonAlphaNumeric = "S!1, 31 October 2019";
        String illegalDayOfWeekNumeric= "132, 31 October 2019";
        String illegalDayOfWeekNonAlphaNumeric2 = "^!), 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeek));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeek1));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeekAndMissingInfo));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeekLong));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeekNonAlphaNumeric));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeekNonAlphaNumeric2));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(illegalDayOfWeekNumeric));


        // invalid month string
        String invalidMonth = "Thu, 31 Octopus 2019";
        String invalidMonth1 = "Sat, 4 Mai 2019";
        String invalidMonth2 = "Sat, 4 5 2019";
        String invalidMonth3 = "Sat, 4 555 2019";
        String invalidMonthNonAlphaNumeric = "Sat, 31 !!!! 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidMonth));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidMonth1));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidMonth2));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidMonthNonAlphaNumeric));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidMonth3));

        // invalid year
        String invalidYear = "Mon, 1 January 1979";
        String invalidYear1 = "Thu, 1 January 2201";
        String invalidYear2 = "Mon, 31 December 1979";
        String invalidYear3 = "Thu, 31 October 0000";
        String invalidYear4 = "Thu, 31 October twen";
        String invalidYear5 = "Thu, 31 October -001";
        String invalidYear6 = "THu, 31 October -0001";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear1));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear2));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear3));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear4));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear5));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYear6));

        // invalid date
        String invalidDate = "Fri, 32 January 2019"; // non-existent date
        String invalidDate1 = "Fri, 31 January 2019"; // day and date mismatch
        String invalidDate2 = "Fri, 29 February 2019"; // non-existent date in non-leap year
        String invalidDate3 = "Sat, 30 February 2019"; // non-existent date in Feb
        String invalidDate4 = "Tue, 31 September 2019"; // non-existent date in Sep (only has 30 days)
        String invalidDate5 = "Fri, 30 February 2024"; // non-existent date in Feb of leap year
        String invalidDate6 = "Sat, 31 February 2024"; // non-existent date in Feb of leap year
        String invalidDate7 = "Fri, 0 July 2023"; // invalid day of month
        String invalidDate8 = "Thu, -1 July 2023";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate1));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate2));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate3));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate4));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate5));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate6));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate7));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDate8));


        // correct format but totally corrupted data
        String formattedRandom = "Bus, !! Spelling hhhh";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(formattedRandom));


        // invalid data with short form for month
        String invalidDateShort = "Fri, 32 Janu 2019"; // non-existent date
        String invalidDateShort1 = "Fri, 31 Jan 2019"; // day and date mismatch
        String invalidDateShort2 = "Fri, 29 Februar 2019"; // non-existent date in non-leap year
        String invalidDateShort3 = "Sat, 30 Febr 2019"; // non-existent date in Feb
        String invalidDateShort4 = "Tue, 31 Sep 2019"; // non-existent date in Sep (only has 30 days)
        String invalidDateShort5 = "Fri, 30 Febru 2024"; // non-existent date in Feb of leap year
        String invalidDateShort6 = "Sat, 31 Feb 2024"; // non-existent date in Feb of leap year
        String invalidDateShort7 = "Fri, 0 Jul 2023"; // invalid day of month
        String invalidDateShort8 = "Thu, -1 Jul 2023";

        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort1));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort2));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort3));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort4));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort5));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort6));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort7));
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidDateShort8));

        String invalidYearShort = "Mon, 1 Janua 1979";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(invalidYearShort));

        // todo: check to ensure that no one calls MonthOfYear.valueOf()
        // todo: whenever you call valueof, make sure that it has passed all checks/will throw exception
    }

    @Test
    public void getInstanceFromString() throws IllegalValueException {
        // with trailing spaces
        String spacingBefore = " Thu, 31 October 2019";
        String spacingAfter = "Thu, 31 October 2019 ";
        Date octDate = new Date(new Day(DayOfWeek.THU, 31, MonthOfYear.OCTOBER, new Year(2019)),
                MonthOfYear.OCTOBER, new Year(2019));
        assertEquals(octDate, Date.getInstanceFromString(spacingBefore));
        assertEquals(octDate, Date.getInstanceFromString(spacingAfter));

        // with incomplete month that is still recognisable
        String shortMonth = "Thu, 31 Octo 2019";
        String shortMonth1 = "Tue, 28 Feb 2023";
        String shortMonth2 = "Thu, 29 Febr 2024";
        String shortMonth3 = "Wed, 13 Jun 2012";

        Date monthDate = new Date(new Day(DayOfWeek.THU, 31, MonthOfYear.OCTOBER, new Year(2019)),
                MonthOfYear.OCTOBER, new Year(2019));
        Date monthDate1 = new Date(new Day(DayOfWeek.TUE, 28, MonthOfYear.FEBRUARY, new Year(2023)),
                MonthOfYear.FEBRUARY, new Year(2023));
        Date monthDate2 = new Date(new Day(DayOfWeek.THU, 29, MonthOfYear.FEBRUARY, new Year(2024)),
                MonthOfYear.FEBRUARY, new Year(2024));
        Date monthDate3 = new Date(new Day(DayOfWeek.WED, 13, MonthOfYear.JUNE, new Year(2012)),
                MonthOfYear.JUNE, new Year(2012));

        assertEquals(monthDate, Date.getInstanceFromString(shortMonth));
        assertEquals(monthDate1, Date.getInstanceFromString(shortMonth1));
        assertEquals(monthDate2, Date.getInstanceFromString(shortMonth2));
        assertEquals(monthDate3, Date.getInstanceFromString(shortMonth3));

        // with complete month
        String completeMonth = "Thu, 31 October 2019";
        String completeMonth1 = "Tue, 28 February 2023";
        String completeMonth2 = "Thu, 29 February 2024";
        String completeMonth3 = "Wed, 13 June 2012";
        assertEquals(monthDate, Date.getInstanceFromString(completeMonth));
        assertEquals(monthDate1, Date.getInstanceFromString(completeMonth1));
        assertEquals(monthDate2, Date.getInstanceFromString(completeMonth2));
        assertEquals(monthDate3, Date.getInstanceFromString(completeMonth3));
    }

    @Test
    public void getPreviousDate() {
        // first of Jan
        Date currDate = new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.JANUARY, new Year(2030)),
                MonthOfYear.JANUARY, new Year(2030));
        Date expectedPrevDate = new Date(new Day(DayOfWeek.MON, 31, MonthOfYear.DECEMBER, new Year(2029)),
                MonthOfYear.DECEMBER, new Year(2029));
        assertEquals(expectedPrevDate, currDate.getPreviousDate());

        // first of May
        Date currDate2 = new Date(new Day(DayOfWeek.MON, 1, MonthOfYear.MAY, new Year(2028)),
                MonthOfYear.MAY, new Year(2028));
        Date expectedPrevDate2 = new Date(new Day(DayOfWeek.SUN, 30, MonthOfYear.APRIL, new Year(2028)),
                MonthOfYear.APRIL, new Year(2028));
        assertEquals(expectedPrevDate2, currDate2.getPreviousDate());

        // first of March (non-leap year)
        Date currDate3 = new Date(new Day(DayOfWeek.FRI, 1, MonthOfYear.MARCH, new Year(2041)),
                MonthOfYear.MARCH, new Year(2041));
        Date expectedPrevDate3 = new Date(new Day(DayOfWeek.THU, 28, MonthOfYear.FEBRUARY, new Year(2041)),
                MonthOfYear.FEBRUARY, new Year(2041));
        assertEquals(expectedPrevDate3, currDate3.getPreviousDate());

        // first of March (non-leap year)
        Date currDate4 = new Date(new Day(DayOfWeek.THU, 1, MonthOfYear.MARCH, new Year(2040)),
                MonthOfYear.MARCH, new Year(2040));
        Date expectedPrevDate4 = new Date(new Day(DayOfWeek.WED, 29, MonthOfYear.FEBRUARY, new Year(2040)),
                MonthOfYear.FEBRUARY, new Year(2040));
        assertEquals(expectedPrevDate4, currDate4.getPreviousDate());

        // Tues to Mon
        Date currDate5 = new Date(new Day(DayOfWeek.TUE, 5, MonthOfYear.NOVEMBER, new Year(2019)),
                MonthOfYear.NOVEMBER, new Year(2019));
        Date expectedPrevDate5 = new Date(new Day(DayOfWeek.MON, 4, MonthOfYear.NOVEMBER,
                new Year(2019)), MonthOfYear.NOVEMBER, new Year(2019));
        assertEquals(expectedPrevDate5, currDate5.getPreviousDate());

        // Mon to Sun
        Date currDate6 = new Date(new Day(DayOfWeek.MON, 5, MonthOfYear.AUGUST, new Year(2019)),
                MonthOfYear.AUGUST, new Year(2019));
        Date expectedPrevDate6 = new Date(new Day(DayOfWeek.SUN, 4, MonthOfYear.AUGUST, new Year(2019)),
                MonthOfYear.AUGUST, new Year(2019));
        assertEquals(expectedPrevDate6, currDate6.getPreviousDate());

        // Sun to Sat
        Date currDate7 = new Date(new Day(DayOfWeek.SUN, 6, MonthOfYear.OCTOBER, new Year(2019)),
                MonthOfYear.OCTOBER, new Year(2019));
        Date expectedPrevDate7 = new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.OCTOBER, new Year(2019)),
                MonthOfYear.OCTOBER, new Year(2019));
        assertEquals(expectedPrevDate7, currDate7.getPreviousDate());
    }

    @Test
    public void getNextDate() {
        // thirty-first of dec
        Date currDate = new Date(new Day(DayOfWeek.WED, 31, MonthOfYear.DECEMBER, new Year(2025)),
                MonthOfYear.DECEMBER, new Year(2025));
        Date expectedNextDate = new Date(new Day(DayOfWeek.THU, 1, MonthOfYear.JANUARY, new Year(2026)),
                MonthOfYear.JANUARY, new Year(2026));
        assertEquals(expectedNextDate, currDate.getNextDate());

        // thirty of June
        Date currDate2 = new Date(new Day(DayOfWeek.SAT, 30, MonthOfYear.JUNE, new Year(2046)),
                MonthOfYear.JUNE, new Year(2046));
        Date expectedNextDate2 = new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.JULY, new Year(2046)),
                MonthOfYear.JULY, new Year(2046));
        assertEquals(expectedNextDate2, currDate2.getNextDate());

        // twenty-eight of feb (non-leap year)
        Date currDate3 = new Date(new Day(DayOfWeek.THU, 28, MonthOfYear.FEBRUARY, new Year(2047)),
                MonthOfYear.FEBRUARY, new Year(2047));
        Date expectedNextDate3 = new Date(new Day(DayOfWeek.FRI, 1, MonthOfYear.MARCH, new Year(2047)),
                MonthOfYear.MARCH, new Year(2047));
        assertEquals(expectedNextDate3, currDate3.getNextDate());

        // twenty-nine of feb (leap year)
        Date currDate4 = new Date(new Day(DayOfWeek.SAT, 29, MonthOfYear.FEBRUARY, new Year(2048)),
                MonthOfYear.FEBRUARY, new Year(2048));
        Date expectedNextDate4 = new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.MARCH, new Year(2048)),
                MonthOfYear.MARCH, new Year(2048));
        assertEquals(expectedNextDate4, currDate4.getNextDate());

        // sun to mon
        Date currDate5 = new Date(new Day(DayOfWeek.SUN, 26, MonthOfYear.SEPTEMBER, new Year(2027)),
                MonthOfYear.SEPTEMBER, new Year(2027));
        Date expectedNextDate5 = new Date(new Day(DayOfWeek.MON, 27, MonthOfYear.SEPTEMBER,
                new Year(2027)), MonthOfYear.SEPTEMBER, new Year(2027));
        assertEquals(expectedNextDate5, currDate5.getNextDate());

        // friday to sat
        Date currDate6 = new Date(new Day(DayOfWeek.FRI, 7, MonthOfYear.JANUARY, new Year(2050)),
                MonthOfYear.JANUARY, new Year(2050));
        Date expectedNextDate6 = new Date(new Day(DayOfWeek.SAT, 8, MonthOfYear.JANUARY, new Year(2050)),
                MonthOfYear.JANUARY, new Year(2050));
        assertEquals(expectedNextDate6, currDate6.getNextDate());

        // sat to sun
        Date currDate7 = new Date(new Day(DayOfWeek.SAT, 26, MonthOfYear.MARCH, new Year(2050)),
                MonthOfYear.MARCH, new Year(2050));
        Date expectedNextDate7 = new Date(new Day(DayOfWeek.SUN, 27, MonthOfYear.MARCH, new Year(2050)),
                MonthOfYear.MARCH, new Year(2050));
        assertEquals(expectedNextDate7, currDate7.getNextDate());

        // fifteen of November
        Date currDate8 = new Date(new Day(DayOfWeek.TUE, 15, MonthOfYear.NOVEMBER, new Year(2050)),
                MonthOfYear.NOVEMBER, new Year(2050));
        Date expectedNextDate8 = new Date(new Day(DayOfWeek.WED, 16, MonthOfYear.NOVEMBER, new Year(2050)),
                MonthOfYear.NOVEMBER, new Year(2050));
        assertEquals(expectedNextDate8, currDate8.getNextDate());
    }

    @Test
    public void compareTo() {
        // Thu, 19 Aug 2060
        Date date = new Date(new Day(DayOfWeek.THU, 19, MonthOfYear.AUGUST, new Year(2060)),
                MonthOfYear.AUGUST, new Year(2060));
        Date dateBefore = new Date(new Day(DayOfWeek.WED, 18, MonthOfYear.AUGUST, new Year(2060)),
                MonthOfYear.AUGUST, new Year(2060));
        Date dateAfter = new Date(new Day(DayOfWeek.FRI, 20, MonthOfYear.AUGUST, new Year(2060)),
                MonthOfYear.AUGUST, new Year(2060));
        Date monthBefore = new Date(new Day(DayOfWeek.MON, 19, MonthOfYear.JULY, new Year(2060)),
                MonthOfYear.JULY, new Year(2060));
        Date monthAfter = new Date(new Day(DayOfWeek.SUN, 19, MonthOfYear.SEPTEMBER, new Year(2060)),
                MonthOfYear.SEPTEMBER, new Year(2060));
        Date yearBefore = new Date(new Day(DayOfWeek.TUE, 19, MonthOfYear.AUGUST, new Year(2059)),
                MonthOfYear.AUGUST, new Year(2059));
        Date yearAfter = new Date(new Day(DayOfWeek.FRI, 19, MonthOfYear.AUGUST, new Year(2061)),
                MonthOfYear.AUGUST, new Year(2061));
        Date sameDate = new Date(new Day(DayOfWeek.THU, 19, MonthOfYear.AUGUST, new Year(2060)),
                MonthOfYear.AUGUST, new Year(2060));

        assertEquals(0, date.compareTo(sameDate));
        assertEquals(0, date.compareTo(date));
        assertTrue(date.compareTo(dateBefore) > 0);
        assertTrue(date.compareTo(dateAfter) < 0);
        assertTrue(date.compareTo(monthBefore) > 0);
        assertTrue(date.compareTo(monthAfter) < 0);
        assertTrue(date.compareTo(yearBefore) > 0);
        assertTrue(date.compareTo(yearAfter) < 0);
    }

    @Test
    public void to_string() {
        Date date = new Date(new Day(DayOfWeek.THU, 19, MonthOfYear.AUGUST, new Year(2060)),
                MonthOfYear.AUGUST, new Year(2060));
        assertEquals("Thu, 19 August 2060", date.toString());
    }
}
