package seedu.billboard.model.statistics;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.GROCERIES;
import static seedu.billboard.testutil.TypicalExpenses.MOVIE;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalExpenses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.testutil.TypicalExpenses;


public class StatisticsGeneratorTest {
    private final StatisticsGenerator statsGenerator = new StatisticsGenerator();

    @Test
    public void generateExpenseTimeline_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> statsGenerator.generateExpenseTimeline(null));
        assertThrows(NullPointerException.class, () ->
                statsGenerator.generateExpenseTimeline(null, null));
        assertThrows(NullPointerException.class, () ->
                statsGenerator.generateExpenseTimeline(Collections.singletonList(TypicalExpenses.BILLS), null));
    }

    @Test
    void generateExpenseTimeline_emptyList_returnsEmptyExpenseTimeline() {
        ExpenseTimeline timeline = statsGenerator.generateExpenseTimeline(new ArrayList<>());

        assertThat(timeline, is(instanceOf(EmptyExpenseTimeline.class)));
    }

    // Based of expenses list from {@code TypicalExpenses#getTypicalExpenses}.
    @Test
    void generateExpenseTimeline_nonEmptyList_returnsCorrectExpenseTimeline() {
        DateInterval interval = DateInterval.MONTH;
        ExpenseTimeline timeline = statsGenerator.generateExpenseTimeline(getTypicalExpenses(), interval);

        assertThat(timeline, is(instanceOf(FilledExpenseTimeline.class))); // check type

        assertEquals(new DateRange(BILLS.getCreated().dateTime.toLocalDate(),
                        GROCERIES.getCreated().dateTime.toLocalDate()),
                timeline.getDateRange()); // check range

        assertEquals(DateInterval.MONTH, timeline.getDateInterval()); // check interval

        // check map
        Map<DateRange, Amount> actualTimeline = timeline.getTimeline();
        DateRange billsDateRange = getAdjustedDateRangeWithIntervalFromExpense(BILLS, interval);
        assertThat(actualTimeline, IsMapContaining.hasEntry(billsDateRange, new Amount("350.25")));

        DateRange foodAndMovieDateRange = getAdjustedDateRangeWithIntervalFromExpense(MOVIE, interval);
        assertThat(actualTimeline, IsMapContaining.hasEntry(foodAndMovieDateRange, new Amount("14.20")));

        DateRange groceriesDateRange = getAdjustedDateRangeWithIntervalFromExpense(GROCERIES, interval);
        assertThat(actualTimeline, IsMapContaining.hasEntry(groceriesDateRange, new Amount("23.50")));
    }

    private DateRange getAdjustedDateRangeWithIntervalFromExpense(Expense expense, DateInterval interval) {
        LocalDate start = expense.getCreated().dateTime.toLocalDate().with(interval.getAdjuster());
        return DateRange.overPeriod(start, interval.getPeriod());
    }
}
