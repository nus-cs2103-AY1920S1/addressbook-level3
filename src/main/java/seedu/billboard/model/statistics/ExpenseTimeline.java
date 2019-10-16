package seedu.billboard.model.statistics;

import java.util.List;
import java.util.Map;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;

/**
 * Interface representing the timeline view of a set of expenses.
 */
public interface ExpenseTimeline {

    /**
     * Getter for date range.
     * @return Date range that the timeline spans.
     */
    DateRange getDateRange();

    /**
     * Getter for time interval used.
     * @return Time interval used, as a {@code DateInterval}.
     */
    DateInterval getDateInterval();

    /**
     * List for the aggregate expenses between {@code start} and {@code end} dates.
     * @return A list of aggregate expenses.
     */
    List<Amount> getTotalAmounts();

    /**
     * Map representing the aggregate expenses over a timeline. The keys are {@code DateRange}s with duration equal to
     * the given interval, and the values are the aggregate expenses over that date range.
     * @return Map representing the timeline.
     */
    Map<DateRange, Amount> getTimeline();
}
