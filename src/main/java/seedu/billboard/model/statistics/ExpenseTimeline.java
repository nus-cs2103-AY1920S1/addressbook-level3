package seedu.billboard.model.statistics;

import java.util.List;

import javafx.util.Pair;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;

/**
 * Interface representing the timeline view of a set of expenses.
 */
public interface ExpenseTimeline {

    /**
     * Getter for time interval used.
     *
     * @return Time interval used, as a {@code DateInterval}.
     */
    DateInterval getDateInterval();

    /**
     * List representing pairs of date ranges to the aggregate expenses over that date range. The keys are
     * {@code DateRange}s with duration equal to the given interval, and the values are the aggregate expenses
     * over that date range.
     *
     * @return List representing the timeline values.
     */
    List<Pair<DateRange, Amount>> getTimelineValues();
}
