package seedu.billboard.model.statistics;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;

/**
 * Represents the aggregate expenses for every interval of time, over a specified range of time
 */
public class FilledExpenseTimeline implements ExpenseTimeline {


    private static final String INVALID_PARAMETERS_MESSAGE = "Number of aggregate expenses and number of date "
            + "intervals within the date range do not match!";

    private final DateRange dateRange;
    private final DateInterval dateInterval;
    private final List<DateRange> timelineIntervals;
    private final List<List<Expense>> aggregateExpenses;

    /**
     * Creates a new ExpenseTimeline with the given parameters.
     *
     * @param dateRange         The range of dates that the timeline spans.
     * @param dateInterval      The level of "granularity" that the aggregate expenses are shown in
     * @param aggregateExpenses List of aggregate expenses. The nth element represents a list of all the expenses
     *                          that occurred between {@code start + n * dateInterval} and {@code start
     *                          + (n + 1) * dateInterval}.
     * @throws IllegalArgumentException if the size of the aggregate expense list and the number of intervals in the
     *                                  date range do not match.
     */
    public FilledExpenseTimeline(DateRange dateRange, DateInterval dateInterval, List<List<Expense>> aggregateExpenses)
            throws IllegalArgumentException {

        requireAllNonNull(dateRange, dateInterval, aggregateExpenses);

        this.dateRange = dateRange;
        this.dateInterval = dateInterval;
        this.timelineIntervals = dateRange.partitionByInterval(dateInterval);
        this.aggregateExpenses = aggregateExpenses;

        if (timelineIntervals.size() != aggregateExpenses.size()) {
            throw new IllegalArgumentException(INVALID_PARAMETERS_MESSAGE);
        }
    }

    /**
     * Getter for date range.
     *
     * @return Date range that the timeline spans.
     */
    public DateRange getDateRange() {
        return dateRange;
    }

    /**
     * Getter for time interval used.
     *
     * @return Time interval used, as a {@code DateInterval}.
     */
    public DateInterval getDateInterval() {
        return dateInterval;
    }

    /**
     * List with each aggregate expense as an {@code Amount} between {@code start} and {@code end} dates.
     *
     * @return A list of aggregate expenses.
     */
    public List<Amount> getTotalAmounts() {
        return aggregateExpenses.stream()
                .map(this::totalAmount)
                .collect(Collectors.toList());
    }

    /**
     * Returns the {@code Amount} representing the total amount of all the expenses.
     *
     * @param expenses List of expenses.
     * @return Total amount.
     */
    private Amount totalAmount(List<Expense> expenses) {
        return expenses.stream()
                .reduce(new Amount("0"), (amount, expense) -> amount.add(expense.getAmount()),
                        Amount::add);
    }

    /**
     * Map representing the aggregate expenses over a timeline. The keys are {@code DateRange}s with duration equal to
     * the given interval, and the values are the aggregate expenses over that date range. The output map has a
     * predictable chronological ordering when iterated.
     *
     * @return Map representing the timeline.
     */
    public Map<DateRange, Amount> getTimeline() {
        return IntStream.range(0, timelineIntervals.size())
                .boxed()
                .collect(Collectors.toMap(timelineIntervals::get,
                    index -> totalAmount(aggregateExpenses.get(index)),
                    Amount::add,
                    LinkedHashMap::new));
    }
}
