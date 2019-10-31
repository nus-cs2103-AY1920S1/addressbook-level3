package seedu.billboard.model.statistics.generators;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.GROCERIES;
import static seedu.billboard.testutil.TypicalExpenses.MOVIE;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalExpenses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.EmptyExpenseTimeline;
import seedu.billboard.model.statistics.formats.ExpenseTimeline;
import seedu.billboard.model.statistics.formats.FilledExpenseTimeline;
import seedu.billboard.testutil.TypicalExpenses;

import javafx.util.Pair;


public class TimelineGeneratorTest {
    private final TimelineGenerator timelineGenerator = new TimelineGenerator();

    @Test
    void generate_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> timelineGenerator.generate(null));
        assertThrows(NullPointerException.class, () ->
                timelineGenerator.generate(null, null));
        assertThrows(NullPointerException.class, () ->
                timelineGenerator.generate(Collections.singletonList(TypicalExpenses.BILLS), null));
    }

    @Test
    void generate_emptyList_returnsEmptyExpenseTimeline() {
        ExpenseTimeline timeline = timelineGenerator.generate(new ArrayList<>());

        assertThat(timeline, is(instanceOf(EmptyExpenseTimeline.class)));
    }

    // Based of expenses list from {@code TypicalExpenses#getTypicalExpenses}.
    @Test
    void generate_nonEmptyList_returnsCorrectExpenseTimeline() {
        DateInterval interval = DateInterval.MONTH;
        ExpenseTimeline timeline = timelineGenerator.generate(getTypicalExpenses(), interval);

        assertThat(timeline, is(instanceOf(FilledExpenseTimeline.class))); // check type

        assertEquals(DateInterval.MONTH, timeline.getDateInterval()); // check interval

        // check pairs list
        List<Pair<DateRange, Amount>> actualTimeline = timeline.getTimelineValues();

        assertThat(actualTimeline, hasItems(
                new Pair<>(getAdjustedDateRangeWithIntervalFromExpense(BILLS, interval), new Amount("350.25")),
                new Pair<>(getAdjustedDateRangeWithIntervalFromExpense(MOVIE, interval), new Amount("14.20")),
                new Pair<>(getAdjustedDateRangeWithIntervalFromExpense(GROCERIES, interval), new Amount("23.50")))
        );
    }

    private DateRange getAdjustedDateRangeWithIntervalFromExpense(Expense expense, DateInterval interval) {
        LocalDate start = expense.getCreated().dateTime.toLocalDate().with(interval.getAdjuster());
        return DateRange.overPeriod(start, interval.getPeriod());
    }
}
