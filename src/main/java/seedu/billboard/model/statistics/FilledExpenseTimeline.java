package seedu.billboard.model.statistics;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.util.Pair;
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

    private final DateInterval dateInterval;
    private final List<DateRange> timelineIntervals;
    private final List<List<Expense>> aggregateExpenses;

    /**
     * Creates a new ExpenseTimeline with the given parameters.
     *
     * @param dateInterval      The level of "granularity" that the aggregate expenses are shown in.
     * @param timelineIntervals List of the various date ranges for each interval in the timeline.
     * @param aggregateExpenses List of aggregate expenses. The nth element represents a list of all the expenses
     *                          that occurred between {@code start + n * dateInterval} and {@code start
     *                          + (n + 1) * dateInterval}.
     * @throws IllegalArgumentException if the size of the aggregate expense list and the number of intervals in the
     *                                  date range do not match.
     */
    public FilledExpenseTimeline(DateInterval dateInterval, List<DateRange> timelineIntervals,
                                 List<List<Expense>> aggregateExpenses)
            throws IllegalArgumentException {

        requireAllNonNull(dateInterval, timelineIntervals, aggregateExpenses);

        this.dateInterval = dateInterval;
        this.timelineIntervals = timelineIntervals;
        this.aggregateExpenses = aggregateExpenses;

        if (timelineIntervals.size() != aggregateExpenses.size()) {
            throw new IllegalArgumentException(INVALID_PARAMETERS_MESSAGE);
        }
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
     * Map representing the aggregate expenses over a timeline. The keys are {@code DateRange}s with duration equal to
     * the given interval, and the values are the aggregate expenses over that date range. The output map has a
     * predictable chronological ordering when iterated.
     *
     * @return Map representing the timeline.
     */
    @Override
    public List<Pair<DateRange, Amount>> getTimelineValues() {
        return IntStream.range(0, timelineIntervals.size())
                .boxed()
                .map(i -> new Pair<>(timelineIntervals.get(i), totalAmount(aggregateExpenses.get(i))))
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
}
