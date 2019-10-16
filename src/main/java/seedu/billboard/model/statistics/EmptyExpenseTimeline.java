package seedu.billboard.model.statistics;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

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
    public DateRange getDateRange() {
        return new DateRange(startDate, endDate);
    }

    @Override
    public DateInterval getDateInterval() {
        return DateInterval.MONTH;
    }

    @Override
    public List<Amount> getTotalAmounts() {
        return List.of(new Amount("0"));
    }

    @Override
    public Map<DateRange, Amount> getTimeline() {
        return Map.of(new DateRange(startDate, endDate), new Amount("0"));
    }
}
