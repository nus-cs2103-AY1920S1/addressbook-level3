package seedu.billboard.commons.core.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.exceptions.IllegalValueException;


public class DateRangeTest {

    private LocalDate startDate = LocalDate.of(2018, 4, 13);
    private LocalDate endDate = LocalDate.of(2019, 4, 13);

    @Test
    public void constructor_givenValidDates_returnsEquivalentDateRange() throws IllegalValueException {
        DateRange dateRange = new DateRange(startDate, endDate);

        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());

        DateRange sameStartAndEndDateRange = new DateRange(startDate, startDate);

        assertEquals(startDate, sameStartAndEndDateRange.getStartDate());
        assertEquals(startDate, sameStartAndEndDateRange.getEndDate());
    }

    @Test
    public void constructor_givenEndDateEarlierThanStartDate_throwsException() {
        assertThrows(IllegalValueException.class, () -> new DateRange(endDate, startDate));
    }

    @Test
    public void partitionByInterval_weeklyInterval_expectedList() throws IllegalValueException {
        DateRange dateRange = new DateRange(startDate, endDate);
        DateInterval weekInterval = DateInterval.WEEK;

        List<DateRange> partitioned = dateRange.partitionByInterval(weekInterval);

        LocalDate expectedStart = startDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate expectedEndExclusive = endDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        long expectedNumberOfIntervals = ChronoUnit.WEEKS.between(expectedStart, expectedEndExclusive);

        assertEquals(expectedNumberOfIntervals, partitioned.size()); // size correct
        assertEquals(expectedStart, partitioned.get(0).getStartDate()); // start date equal
        assertEquals(expectedEndExclusive.minus(Period.ofDays(1)),
                partitioned.get(partitioned.size() - 1).getEndDate()); // end date equal

        for (var range : partitioned) { // all date ranges correct
            assertEquals(weekInterval.getPeriod(),
                    periodBetweenInclusive(range.getStartDate(), range.getEndDate()));
        }
    }

    @Test void partitionByInterval_monthlyInterval_expectedList() throws IllegalValueException {
        LocalDate startDate = LocalDate.of(2018, Month.DECEMBER, 6);
        LocalDate endDate = LocalDate.of(2019, Month.FEBRUARY, 13);
        DateRange range = new DateRange(startDate, endDate);

        List<DateRange> partitioned = range.partitionByInterval(DateInterval.MONTH);

        assertEquals(3, partitioned.size());

        // contains December
        DateRange dec = partitioned.get(0);
        assertEquals(LocalDate.of(2018, Month.DECEMBER, 1), dec.getStartDate());
        assertEquals(LocalDate.of(2018, Month.DECEMBER, 31), dec.getEndDate());

        // contains January
        DateRange jan = partitioned.get(1);
        assertEquals(LocalDate.of(2019, Month.JANUARY, 1), jan.getStartDate());
        assertEquals(LocalDate.of(2019, Month.JANUARY, 31), jan.getEndDate());

        // contains February
        DateRange feb = partitioned.get(2);
        assertEquals(LocalDate.of(2019, Month.FEBRUARY, 1), feb.getStartDate());
        assertEquals(LocalDate.of(2019, Month.FEBRUARY, 28), feb.getEndDate());
    }



    @Test
    public void partitionByInterval_intervalLargerThanDateRange_listOfSizeOne() throws IllegalValueException {
        DateRange dateRange = new DateRange(LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 2));
        DateInterval yearInterval = DateInterval.YEAR;

        List<DateRange> partitioned = dateRange.partitionByInterval(yearInterval);

        assertEquals(1, partitioned.size());
        DateRange dateRangeOfInterval = partitioned.get(0);
        assertEquals(yearInterval.getPeriod(),
                periodBetweenInclusive(dateRangeOfInterval.getStartDate(), dateRangeOfInterval.getEndDate()));
    }

    @Test
    public void equals() throws IllegalValueException {
        DateRange range1 = new DateRange(startDate, endDate);
        DateRange range2 = new DateRange(startDate, endDate);

        assertEquals(range1, range2); // same start and end dates

        DateRange range3 = new DateRange(startDate, LocalDate.of(2018, 6, 12));

        assertNotEquals(range3, range1); // different end date

        DateRange range4 = new DateRange(LocalDate.MIN, endDate);

        assertNotEquals(range4, range1); // different start date

        DateRange range5 = new DateRange(LocalDate.MIN, LocalDate.MAX);

        assertNotEquals(range5, range1); // different start and end dates
    }

    private Period periodBetweenInclusive(LocalDate startDateInclusive, LocalDate endDateInclusive) {
        return Period.between(startDateInclusive, endDateInclusive.plus(Period.ofDays(1)));
    }
}
