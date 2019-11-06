package seedu.address.calendar.model.date;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // null day of week
        assertThrows(NullPointerException.class, () -> new Day(null, 1, MonthOfYear.DECEMBER,
                new Year(2019)));
        // null month of year
        assertThrows(NullPointerException.class, () -> new Day(DayOfWeek.SUN, 1, null,
                new Year(2019)));
        // null year
        assertThrows(NullPointerException.class, () -> new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
                null));
        // null year with inconsistent information
        assertThrows(NullPointerException.class, () -> new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
                null));
    }

    @Test
    public void constructor_invalidDayOfMonth_throwsIllegalArgumentException() {
        Year leapYear = new Year(2020);
        Year leapYearBefore = new Year(2019);
        Year leapYearAfter = new Year(2021);

        MonthOfYear monthWith31Days = MonthOfYear.JANUARY;
        MonthOfYear monthWith30Days = MonthOfYear.SEPTEMBER;
        MonthOfYear february = MonthOfYear.FEBRUARY;

        // day of month less than minimum (leap year)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, Day.BOUND_LOWER - 1,
                monthWith30Days, leapYear));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, Integer.MIN_VALUE,
                monthWith30Days, leapYear));
        // day of month less than minimum (non-leap year)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, Day.BOUND_LOWER - 1,
                monthWith30Days, leapYearAfter));
        // day of month is more than maximum (for month with 31 days)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, 32, monthWith31Days,
                leapYear));
        // day of month is more than maximum (for month with 30 days)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, 31, monthWith30Days,
                leapYear));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, 32, monthWith30Days,
                leapYearBefore));
        // day of month is more than maximum (for month with 29 days)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, 30, february, leapYear));
        // day of month is more than maximum (for month with 28 days, before leap year)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, 29, february,
                leapYearAfter));
        // day of month is more than maximum (for month with 28 days, after leap year)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, 29, february,
                leapYearBefore));
        // day of month is maximum integer value (leap year)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, Integer.MAX_VALUE, monthWith31Days,
                leapYearBefore));
        // day of month is maximum integer value (non-leap year)
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SUN, Integer.MAX_VALUE, february,
                leapYearBefore));
    }

    @Test
    public void constructor_invalidDayOfWeek_throwsIllegalArgumentException() {
        Year leapYear = new Year(2136);
        Year leapYearBefore = new Year(2135);
        Year leapYearAfter = new Year(2137);

        MonthOfYear february = MonthOfYear.FEBRUARY;
        MonthOfYear july = MonthOfYear.JULY;

        // incorrect day of week for leap year and february
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.MON, 1, february, leapYear));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.MON, 29, february, leapYear));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.MON, 15, february, leapYear));
        // incorrect day of week for leap year and july
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.THU, 1, july, leapYear));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.THU, 31, july, leapYear));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.THU, 15, july, leapYear));

        // incorrect day of week for non-leap year and february
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.WED, 1, february,
                leapYearBefore));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.WED, 28, february,
                leapYearAfter));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.WED, 15, february,
                leapYearBefore));
        // incorrect day of week for leap year and july
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SAT, 1, july,
                leapYearAfter));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SAT, 31, july,
                leapYearBefore));
        assertThrows(IllegalArgumentException.class, () -> new Day(DayOfWeek.SAT, 15, july,
                leapYearAfter));
    }

    @Test
    public void getDay_null_throwsNullPointerException() {
        // null month of year
        assertThrows(NullPointerException.class, () -> Day.getDay(1, null, new Year(2023)));
        // null year
        assertThrows(NullPointerException.class, () -> Day.getDay(1, MonthOfYear.DECEMBER, null));
    }

    @Test
    public void getDay_invalidDayOfMonth_throwsIllegalArgumentException() {
        Year leapYear = new Year(2020);
        Year leapYearBefore = new Year(2019);
        Year leapYearAfter = new Year(2021);

        MonthOfYear monthWith31Days = MonthOfYear.JANUARY;
        MonthOfYear monthWith30Days = MonthOfYear.SEPTEMBER;
        MonthOfYear february = MonthOfYear.FEBRUARY;

        // day of month less than minimum (leap year)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(Day.BOUND_LOWER - 1, monthWith30Days,
                leapYear));
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(Integer.MIN_VALUE, monthWith30Days, leapYear));
        // day of month less than minimum (non-leap year)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(Day.BOUND_LOWER - 1, monthWith30Days,
                leapYearAfter));
        // day of month is more than maximum (for month with 31 days)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(32, monthWith31Days, leapYear));
        // day of month is more than maximum (for month with 30 days)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(31, monthWith30Days, leapYear));
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(32, monthWith30Days, leapYearBefore));
        // day of month is more than maximum (for month with 29 days)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(30, february, leapYear));
        // day of month is more than maximum (for month with 28 days, before leap year)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(29, february, leapYearAfter));
        // day of month is more than maximum (for month with 28 days, after leap year)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(29, february, leapYearBefore));
        // day of month is maximum integer value (leap year)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(Integer.MAX_VALUE, monthWith31Days,
                leapYearBefore));
        // day of month is maximum integer value (non-leap year)
        assertThrows(IllegalArgumentException.class, () -> Day.getDay(Integer.MAX_VALUE, february, leapYearBefore));
    }

    @Test
    public void getDay() {
        // 1 Feb 2022 Tue
        Day firstFeb2022 = new Day(DayOfWeek.TUE, 1, MonthOfYear.FEBRUARY, new Year(2022));
        assertEquals(firstFeb2022, Day.getDay(1, MonthOfYear.FEBRUARY, new Year(2022)));

        // 28 Feb 2023 Tue
        Day twentyEightFeb2023 = new Day(DayOfWeek.TUE, 28, MonthOfYear.FEBRUARY, new Year(2023));
        assertEquals(twentyEightFeb2023, Day.getDay(28, MonthOfYear.FEBRUARY, new Year(2023)));

        // 29 Feb 2024 Thu
        Day twentyNineFeb2024 = new Day(DayOfWeek.THU, 29, MonthOfYear.FEBRUARY, new Year(2024));
        assertEquals(twentyNineFeb2024, Day.getDay(29, MonthOfYear.FEBRUARY, new Year(2024)));

        // 1 March 2022 Tue
        Day firstMar2022 = new Day(DayOfWeek.TUE, 1, MonthOfYear.MARCH, new Year(2022));
        assertEquals(firstMar2022, Day.getDay(1, MonthOfYear.MARCH, new Year(2022)));

        // 1 March 2023 Wed
        Day firstMar2023 = new Day(DayOfWeek.WED, 1, MonthOfYear.MARCH, new Year(2023));
        assertEquals(firstMar2023, Day.getDay(1, MonthOfYear.MARCH, new Year(2023)));

        // 1 March 2024 Fri
        Day firstMar2024 = new Day(DayOfWeek.FRI, 1, MonthOfYear.MARCH, new Year(2024));
        assertEquals(firstMar2024, Day.getDay(1, MonthOfYear.MARCH, new Year(2024)));

        // 15 April 2100 Thu
        Day fifteenApr2100 = new Day(DayOfWeek.THU, 15, MonthOfYear.APRIL, new Year(2100));
        assertEquals(fifteenApr2100, Day.getDay(15, MonthOfYear.APRIL, new Year(2100)));

        // 30 Nov 2019 Sat
        Day thirtyNov2019 = new Day(DayOfWeek.SAT, 30, MonthOfYear.NOVEMBER, new Year(2019));
        assertEquals(thirtyNov2019, Day.getDay(30, MonthOfYear.NOVEMBER, new Year(2019)));

        // 31 Dec 2019 Tue
        Day thirtyFirstDec2019 = new Day(DayOfWeek.TUE, 31, MonthOfYear.DECEMBER, new Year(2019));
        assertEquals(thirtyFirstDec2019, Day.getDay(31, MonthOfYear.DECEMBER, new Year(2019)));

        // 1 Dec 2019 Sun
        Day firstDec2019 = new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER, new Year(2019));
        assertEquals(firstDec2019, Day.getDay(1, MonthOfYear.DECEMBER, new Year(2019)));

        // 1 Jan 2135 Wed
        Day firstJan2135 = new Day(DayOfWeek.SAT, 1, MonthOfYear.JANUARY, new Year(2135));
        assertEquals(firstJan2135, Day.getDay(1, MonthOfYear.JANUARY, new Year(2135)));

        // 1 Jan 1980 Tue
        Day firstJan1980 = new Day(DayOfWeek.TUE, 1, MonthOfYear.JANUARY, new Year(Year.BOUND_LOWER));
        assertEquals(firstJan1980, Day.getDay(1, MonthOfYear.JANUARY, new Year(Year.BOUND_LOWER)));

        // 31 Dec 2200 Wed
        Day thirtyFirstDec2200 = new Day(DayOfWeek.WED, 31, MonthOfYear.DECEMBER,
                new Year(Year.BOUND_UPPER));
        assertEquals(thirtyFirstDec2200, Day.getDay(31, MonthOfYear.DECEMBER,  new Year(Year.BOUND_UPPER)));
    }

    @Test
    public void getDaysOfMonth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Day.getDaysOfMonth(null, new Year(2019)));
        assertThrows(NullPointerException.class, () -> Day.getDaysOfMonth(MonthOfYear.JUNE, null));
    }

    @Test
    public void getDaysOfMonthTest() {
        List<Day> daysFeb2020 = new ArrayList<>();
        MonthOfYear february = MonthOfYear.FEBRUARY;
        Year year2020 = new Year(2020);
        IntStream.range(0, 29)
                .mapToObj(i -> {
                    Calendar date = Calendar.getInstance();
                    date.set(2020, 1, i + 1);
                    int dayOfWeekInt = date.get(Calendar.DAY_OF_WEEK);
                    DayOfWeek dayOfWeek = DateUtil.toDayOfWeek(dayOfWeekInt - 1);
                    return new Day(dayOfWeek, i + 1, february, year2020);
                })
                .forEach(daysFeb2020::add);
        assertIterableEquals(Day.getDaysOfMonth(february, year2020), daysFeb2020);

        List<Day> daysDec2200 = new ArrayList<>();
        MonthOfYear december = MonthOfYear.DECEMBER;
        Year year2200 = new Year(2200);
        IntStream.range(0, 31)
                .mapToObj(i -> {
                    Calendar date = Calendar.getInstance();
                    date.set(2200, 11, i + 1);
                    int dayOfWeekInt = date.get(Calendar.DAY_OF_WEEK);
                    DayOfWeek dayOfWeek = DateUtil.toDayOfWeek(dayOfWeekInt - 1);
                    return new Day(dayOfWeek, i + 1, december, year2200);
                })
                .forEach(daysDec2200::add);
        assertIterableEquals(Day.getDaysOfMonth(december, year2200), daysDec2200);

        List<Day> daysFeb1980 = new ArrayList<>();
        Year year1980 = new Year(1980);
        IntStream.range(0, 29)
                .mapToObj(i -> {
                    Calendar date = Calendar.getInstance();
                    date.set(1980, 1, i + 1);
                    int dayOfWeekInt = date.get(Calendar.DAY_OF_WEEK);
                    DayOfWeek dayOfWeek = DateUtil.toDayOfWeek(dayOfWeekInt - 1);
                    return new Day(dayOfWeek, i + 1, february, year1980);
                })
                .forEach(daysFeb1980::add);
        assertIterableEquals(Day.getDaysOfMonth(february, year1980), daysFeb1980);

        List<Day> daysFeb2141 = new ArrayList<>();
        Year year2141 = new Year(2141);
        IntStream.range(0, 28)
                .mapToObj(i -> {
                    Calendar date = Calendar.getInstance();
                    date.set(2141, 1, i + 1);
                    int dayOfWeekInt = date.get(Calendar.DAY_OF_WEEK);
                    DayOfWeek dayOfWeek = DateUtil.toDayOfWeek(dayOfWeekInt - 1);
                    return new Day(dayOfWeek, i + 1, february, year2141);
                })
                .forEach(daysFeb2141::add);
        assertIterableEquals(Day.getDaysOfMonth(february, year2141), daysFeb2141);
    }

    @Test
    public void isValidDay() {
        // day of month is not within limits
        assertFalse(Day.isValidDay(DayOfWeek.THU, Day.BOUND_LOWER, MonthOfYear.NOVEMBER, new Year(2019)));
        assertFalse(Day.isValidDay(DayOfWeek.THU, 31, MonthOfYear.NOVEMBER, new Year(2019)));
        assertFalse(Day.isValidDay(DayOfWeek.TUE, 29, MonthOfYear.FEBRUARY, new Year(2019)));
        assertFalse(Day.isValidDay(DayOfWeek.TUE, 30, MonthOfYear.FEBRUARY, new Year(2019)));
        assertFalse(Day.isValidDay(DayOfWeek.TUE, 30, MonthOfYear.FEBRUARY, new Year(2020)));
        assertFalse(Day.isValidDay(DayOfWeek.TUE, 31, MonthOfYear.FEBRUARY, new Year(2020)));

        // day of week does not match
        assertFalse(Day.isValidDay(DayOfWeek.TUE, 16, MonthOfYear.MAY, new Year(2011)));
        assertFalse(Day.isValidDay(DayOfWeek.TUE, 18, MonthOfYear.MAY, new Year(2011)));

        // no error
        assertTrue(Day.isValidDay(DayOfWeek.TUE, 1, MonthOfYear.FEBRUARY, new Year(2022)));
        assertTrue(Day.isValidDay(DayOfWeek.TUE, 28, MonthOfYear.FEBRUARY, new Year(2023)));
        assertTrue(Day.isValidDay(DayOfWeek.THU, 29, MonthOfYear.FEBRUARY, new Year(2024)));
        assertTrue(Day.isValidDay(DayOfWeek.TUE, 1, MonthOfYear.MARCH, new Year(2022)));
        assertTrue(Day.isValidDay(DayOfWeek.WED, 1, MonthOfYear.MARCH, new Year(2023)));
        assertTrue(Day.isValidDay(DayOfWeek.THU, 15, MonthOfYear.APRIL, new Year(2100)));
    }

    @Test
    public void equals() {
        assertNotEquals(null, new Day(DayOfWeek.TUE, 17, MonthOfYear.MAY, new Year(2011)));
        assertNotEquals(new Day(DayOfWeek.TUE, 17, MonthOfYear.MAY, new Year(2011)), new Day(DayOfWeek.WED,
                18, MonthOfYear.MAY, new Year(2011)));
        assertEquals(new Day(DayOfWeek.TUE, 17, MonthOfYear.MAY, new Year(2011)),
                new Day(DayOfWeek.TUE, 17, MonthOfYear.MAY, new Year(2011)));

    }
}
