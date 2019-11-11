package seedu.billboard.commons.core.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.Test;


public class DateRangeTest {

    private LocalDate startDate = LocalDate.of(2018, 4, 13);
    private LocalDate endDate = LocalDate.of(2019, 4, 13);

    @Test
    public void from_givenValidDates_returnsEquivalentDateRange() {
        DateRange dateRange = DateRange.from(startDate, endDate);

        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());
        assertEquals(endDate.minus(Period.ofDays(1)), dateRange.getEndDateInclusive());

        DateRange minAndMax = DateRange.from(LocalDate.MIN, LocalDate.MAX);

        assertEquals(LocalDate.MIN, minAndMax.getStartDate());
        assertEquals(LocalDate.MAX, minAndMax.getEndDate());
    }

    @Test
    public void from_givenInvalidDates_throwsException() {
        assertThrows(DateTimeException.class, () -> DateRange.from(endDate, startDate));
        assertThrows(DateTimeException.class, () ->
                DateRange.from(startDate, startDate.minus(Period.ofDays(1))));

        assertThrows(NullPointerException.class, () -> DateRange.from(null, startDate));
        assertThrows(NullPointerException.class, () -> DateRange.from(endDate, null));
        assertThrows(NullPointerException.class, () -> DateRange.from(null, null));
    }

    @Test
    public void fromClosed_givenValidDates_returnsEquivalentDateRange() {
        DateRange dateRange = DateRange.fromClosed(startDate, endDate);

        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate.plus(Period.ofDays(1)), dateRange.getEndDate());
        assertEquals(endDate, dateRange.getEndDateInclusive());

        DateRange sameStartAndEndDateRange = DateRange.fromClosed(startDate, startDate);

        assertEquals(startDate, sameStartAndEndDateRange.getStartDate());
        assertEquals(startDate, sameStartAndEndDateRange.getEndDateInclusive());

        // represents an empty date -> fromClosed(date, date - 1) is the same as from(date, date) which is a valid
        // (empty) date.
        DateRange endOneBeforeStart = DateRange.fromClosed(startDate, startDate.minus(Period.ofDays(1)));

        assertEquals(startDate, endOneBeforeStart.getStartDate());
        assertEquals(startDate, endOneBeforeStart.getEndDate());
        assertEquals(startDate.minus(Period.ofDays(1)), endOneBeforeStart.getEndDateInclusive());

        // Date should overflow
        assertThrows(DateTimeException.class, () -> DateRange.fromClosed(LocalDate.MAX, LocalDate.MAX));
    }

    @Test
    public void fromClosed_givenEndDateEarlierThanStartDate_throwsException() {
        assertThrows(DateTimeException.class, () -> DateRange.fromClosed(endDate, startDate));
        assertThrows(DateTimeException.class, () ->
                DateRange.fromClosed(startDate, startDate.minus(Period.ofDays(2))));
    }

    @Test
    public void overPeriod() {
        // normal period
        DateRange actual = DateRange.overPeriod(startDate, Period.of(0, 1, 2));
        DateRange expected = DateRange.fromClosed(startDate, LocalDate.of(2018, 5, 14));
        assertEquals(expected, actual);

        // zero period
        DateRange zeroActual = DateRange.overPeriod(startDate, Period.ZERO);
        DateRange zeroExpected = DateRange.from(startDate, startDate);
        assertEquals(zeroExpected, zeroActual);

        // negative period
        assertThrows(DateTimeException.class, () ->
                DateRange.overPeriod(startDate, Period.of(-1, 1, 1)));
    }

    @Test
    public void contains() {
        DateRange dateRange = DateRange.fromClosed(startDate, endDate);

        assertTrue(dateRange.contains(LocalDate.of(2019, 1, 6)));
        assertFalse(dateRange.contains(LocalDate.of(2019, 4, 14)));

        // value same as start date, returns true
        assertTrue(dateRange.contains(startDate));

        // value same as end date, returns true
        assertTrue(dateRange.contains(endDate));

        // One day date range contains itself
        assertTrue(DateRange.fromClosed(startDate, startDate).contains(startDate));
    }

    @Test
    public void partitionByInterval_weeklyInterval_expectedList() {
        DateRange dateRange = DateRange.fromClosed(startDate, endDate);
        DateInterval weekInterval = DateInterval.WEEK;

        List<DateRange> partitioned = dateRange.partitionByInterval(weekInterval);

        LocalDate expectedStart = startDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate expectedEndExclusive = endDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        long expectedNumberOfIntervals = ChronoUnit.WEEKS.between(expectedStart, expectedEndExclusive);

        assertEquals(expectedNumberOfIntervals, partitioned.size()); // size correct
        assertEquals(expectedStart, partitioned.get(0).getStartDate()); // start date equal
        assertEquals(expectedEndExclusive, partitioned.get(partitioned.size() - 1).getEndDate()); // end date equal

        for (var range : partitioned) { // all date ranges correct
            assertEquals(weekInterval.getPeriod(),
                    periodBetweenInclusive(range.getStartDate(), range.getEndDateInclusive()));
        }
    }

    @Test void partitionByInterval_monthlyInterval_expectedList() {
        LocalDate startDate = LocalDate.of(2018, Month.DECEMBER, 6);
        LocalDate endDate = LocalDate.of(2019, Month.FEBRUARY, 13);
        DateRange range = DateRange.fromClosed(startDate, endDate);

        List<DateRange> partitioned = range.partitionByInterval(DateInterval.MONTH);

        assertEquals(3, partitioned.size());

        // contains December
        DateRange dec = partitioned.get(0);
        assertEquals(LocalDate.of(2018, Month.DECEMBER, 1), dec.getStartDate());
        assertEquals(LocalDate.of(2018, Month.DECEMBER, 31), dec.getEndDateInclusive());

        // contains January
        DateRange jan = partitioned.get(1);
        assertEquals(LocalDate.of(2019, Month.JANUARY, 1), jan.getStartDate());
        assertEquals(LocalDate.of(2019, Month.JANUARY, 31), jan.getEndDateInclusive());

        // contains February
        DateRange feb = partitioned.get(2);
        assertEquals(LocalDate.of(2019, Month.FEBRUARY, 1), feb.getStartDate());
        assertEquals(LocalDate.of(2019, Month.FEBRUARY, 28), feb.getEndDateInclusive());
    }



    @Test
    public void partitionByInterval_intervalLargerThanDateRange_listOfSizeOne() {
        DateRange dateRange = DateRange.fromClosed(LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 2));
        DateInterval yearInterval = DateInterval.YEAR;

        List<DateRange> partitioned = dateRange.partitionByInterval(yearInterval);

        assertEquals(1, partitioned.size());
        DateRange dateRangeOfInterval = partitioned.get(0);
        assertEquals(yearInterval.getPeriod(),
                periodBetweenInclusive(dateRangeOfInterval.getStartDate(), dateRangeOfInterval.getEndDateInclusive()));
    }

    @Test
    public void equals() {
        DateRange range1 = DateRange.fromClosed(startDate, endDate);
        DateRange range2 = DateRange.fromClosed(startDate, endDate);

        assertEquals(range1, range2); // same start and end dates

        DateRange range3 = DateRange.fromClosed(startDate, LocalDate.of(2018, 6, 12));

        assertNotEquals(range3, range1); // different end date

        DateRange range4 = DateRange.fromClosed(LocalDate.MIN, endDate);

        assertNotEquals(range4, range1); // different start date

        DateRange range5 = DateRange.overPeriod(LocalDate.MIN, Period.of(1, 2, 3));

        assertNotEquals(range5, range1); // different start and end dates
    }

    private Period periodBetweenInclusive(LocalDate startDateInclusive, LocalDate endDateInclusive) {
        return Period.between(startDateInclusive, endDateInclusive.plus(Period.ofDays(1)));
    }
}
