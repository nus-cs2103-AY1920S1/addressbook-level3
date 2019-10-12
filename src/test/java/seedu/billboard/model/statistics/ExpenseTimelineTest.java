package seedu.billboard.model.statistics;

import static org.hamcrest.MatcherAssert.assertThat;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.expense.Amount;


public class ExpenseTimelineTest {

    @Test
    public void constructor_numberOfIntervalsAndSizeOfAggregateExpenseListMismatch_throwsException()
            throws IllegalValueException {
        DateRange dateRange = new DateRange(LocalDate.of(2016, 6, 12),
                LocalDate.of(2017, 6, 21));
        DateInterval interval = DateInterval.MONTH;

        List<Amount> amounts = IntStream.range(0, 11)
                .mapToObj(i -> new Amount(i + ""))
                .collect(Collectors.toList());

        assertThrows(IllegalValueException.class, () -> new ExpenseTimeline(dateRange, interval, amounts));
    }

    @Test
    public void getTimeline_oneInterval_expectedMap() throws IllegalValueException {
        DateRange dateRange = new DateRange(LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 6));

        DateInterval interval = DateInterval.WEEK;
        List<Amount> amounts = Collections.singletonList(new Amount("1.50"));

        Map<DateRange, Amount> actual = new ExpenseTimeline(dateRange, interval, amounts).getTimeline();

        assertThat(actual,
                IsMapContaining.hasEntry(dateRange.partitionByInterval(interval).get(0), new Amount("1.50")));
    }

    @Test
    public void getTimeline_manyIntervals_expectedMap() throws IllegalValueException {
        DateRange dateRange = new DateRange(LocalDate.of(2000, 1, 4),
                LocalDate.of(2000, 12, 2));

        DateInterval interval = DateInterval.MONTH;
        List<Amount> amounts = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> new Amount(i + ".20"))
                .collect(Collectors.toList());

        Map<DateRange, Amount> actual = new ExpenseTimeline(dateRange, interval, amounts).getTimeline();

        for (int i = 1; i <= 12; i++) {
            DateRange range = new DateRange(LocalDate.of(2000, i, 1),
                    LocalDate.of(2000, i, 1).with(TemporalAdjusters.lastDayOfMonth()));
            Amount amount = new Amount(i + ".20");

            assertThat(actual, IsMapContaining.hasEntry(range, amount));
        }

    }
}
