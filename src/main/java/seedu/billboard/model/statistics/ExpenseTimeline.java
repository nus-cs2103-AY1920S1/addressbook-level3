package seedu.billboard.model.statistics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.expense.Amount;

/**
 * Represents the aggregate expenses for every interval of time, over a specified range of time
 */
public class ExpenseTimeline {

    private static final String INVALID_PARAMETERS_MESSAGE = "Number of aggregate expenses and number of date "
            + "intervals within the date range do not match!";

    private final DateRange dateRange;
    private final DateInterval dateInterval;
    private final List<DateRange> timelineIntervals;
    private final List<Amount> aggregateExpenses;

    /**
     * Creates a new ExpenseTimeline with the given parameters.
     * @param dateRange The range of dates that the timeline spans.
     * @param dateInterval The level of "granularity" that the aggregate expenses are shown in
     * @param aggregateExpenses List of aggregate amounts. The nth element represents the aggregate expense of all
     *                          expenses that occurred between {@code start + n * dateInterval} and {@code start
     *                          + (n + 1) * dateInterval}.
     * @throws IllegalValueException if the size of the aggregate expense list and the number of intervals in the date
     *                               range do not match.
     */
    public ExpenseTimeline(DateRange dateRange, DateInterval dateInterval, List<Amount> aggregateExpenses)
            throws IllegalValueException {
        this.dateRange = dateRange;
        this.dateInterval = dateInterval;
        this.timelineIntervals = dateRange.partitionByInterval(dateInterval);
        this.aggregateExpenses = aggregateExpenses;

        if (timelineIntervals.size() != aggregateExpenses.size()) {
            throw new IllegalValueException(INVALID_PARAMETERS_MESSAGE);
        }
    }

    /**
     * Getter for date range.
     * @return Date range that the timeline spans.
     */
    public DateRange getDateRange() {
        return dateRange;
    }

    /**
     * Getter for time interval used.
     * @return Time interval used, as a {@code ChronoUnit}.
     */
    public DateInterval getDateInterval() {
        return dateInterval;
    }

    /**
     * List for the aggregate expenses between {@code start} and {@code end} dates.
     * @return A list of aggregate expenses.
     */
    public List<Amount> getAggregateExpenses() {
        return aggregateExpenses;
    }

    /**
     * Map representing the aggregate expenses over a timeline. The keys are {@code DateRange}s with duration equal to
     * the given interval, and the values are the aggregate expenses over that date range.
     * @return Map representing the timeline.
     */
    public Map<DateRange, Amount> getTimeline() {
        return IntStream.range(0, timelineIntervals.size())
                .boxed()
                .collect(Collectors.toMap(timelineIntervals::get, aggregateExpenses::get));
    }
}
