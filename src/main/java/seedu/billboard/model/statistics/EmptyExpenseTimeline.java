package seedu.billboard.model.statistics;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;

import javafx.util.Pair;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;

/**
 * Class representing an {@code ExpenseTimeline} with no expenses to show.
 */
public class EmptyExpenseTimeline implements ExpenseTimeline {

    private LocalDate startDate;
    private LocalDate endDate;

    public EmptyExpenseTimeline() {
        endDate = LocalDate.now();
        startDate = endDate.minus(Period.ofMonths(1));
    }

    @Override
    public DateInterval getDateInterval() {
        return DateInterval.MONTH;
    }

    @Override
    public List<Pair<DateRange, Amount>> getTimelineValues() {
        return Collections.singletonList(new Pair<>(DateRange.fromClosed(startDate, endDate), new Amount("0")));
    }
}
