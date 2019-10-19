package seedu.billboard.model.statistics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;

import javafx.util.Pair;

public class FilledExpenseTimelineTest {

    private final Function<Amount, Expense> expenseFromAmount =
        amount -> new Expense(new Name("blank"),
                new Description(""),
                amount,
                new CreatedDateTime("01/01/2019"), new HashSet<>());

    @Test
    public void constructor_numberOfIntervalsAndSizeOfAggregateExpenseListMismatch_throwsException() {
        DateRange dateRange = new DateRange(LocalDate.of(2016, 6, 12),
                LocalDate.of(2017, 6, 21));
        DateInterval interval = DateInterval.MONTH; // 12 months

        List<List<Expense>> aggregateExpenses = IntStream.range(0, 11)
                .mapToObj(i -> Collections.singletonList(expenseFromAmount.apply(new Amount(i + ".70"))))
                .collect(Collectors.toList()); // 11 aggregate expenses

        assertThrows(IllegalArgumentException.class, () ->
                new FilledExpenseTimeline(interval, dateRange.partitionByInterval(interval), aggregateExpenses));
    }

    @Test
    public void getTimeline_oneInterval_expectedMap() {
        DateRange dateRange = new DateRange(LocalDate.of(2019, 1, 1),
                LocalDate.of(2019, 1, 6));

        DateInterval interval = DateInterval.WEEK;
        List<List<Expense>> aggregateExpense = Collections.singletonList(
                Collections.singletonList(expenseFromAmount.apply(new Amount("1.50"))));

        List<Pair<DateRange, Amount>> actual = new FilledExpenseTimeline(
                interval, dateRange.partitionByInterval(interval), aggregateExpense)
                .getTimelineValues();

        assertThat(actual, hasItem(new Pair<>(dateRange.partitionByInterval(interval).get(0), new Amount("1.50"))));
    }

    @Test
    public void getTimeline_manyIntervals_expectedMap() {
        DateRange dateRange = new DateRange(LocalDate.of(2000, 1, 4),
                LocalDate.of(2000, 12, 2));

        DateInterval interval = DateInterval.MONTH;
        List<List<Expense>> aggregateExpenses = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> Collections.singletonList(expenseFromAmount.apply(new Amount(i + ".20"))))
                .collect(Collectors.toList());

        List<Pair<DateRange, Amount>> actual = new FilledExpenseTimeline(
                interval, dateRange.partitionByInterval(interval), aggregateExpenses)
                .getTimelineValues();

        for (int i = 1; i <= 12; i++) {
            DateRange range = new DateRange(LocalDate.of(2000, i, 1),
                    LocalDate.of(2000, i, 1).with(TemporalAdjusters.lastDayOfMonth()));
            Amount amount = new Amount(i + ".20");

            assertEquals(new Pair<>(range, amount), actual.get(i - 1)); // zero based
        }

    }
}
