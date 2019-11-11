package seedu.billboard.model.statistics.generators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.concurrent.Task;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.util.CollectionUtil;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.EmptyExpenseTimeline;
import seedu.billboard.model.statistics.formats.ExpenseTimeline;
import seedu.billboard.model.statistics.formats.FilledExpenseTimeline;

/**
 * Stateless class to generate a timeline of aggregate expenses. Methods here are guaranteed to have no side effects,
 * or depend on external state.
 */
public class TimelineGenerator implements StatisticsGenerator<ExpenseTimeline> {

    /**
     * Generates an {@code ExpenseTimeline} using a default date interval of a month.
     */
    @Override
    public ExpenseTimeline generate(List<? extends Expense> expenses) {
        return generate(expenses, DateInterval.MONTH);
    }

    /**
     * Generates an {@code ExpenseTimeline} based on the input expenses and the specified date interval.
     * @param expenses Input expenses.
     * @param interval Specified date interval.
     * @return A representation of the expenses as a timeline
     */
    public ExpenseTimeline generate(List<? extends Expense> expenses, DateInterval interval) {
        if (!CollectionUtil.checkNonEmpty(expenses)) {
            return new EmptyExpenseTimeline();
        }

        List<Expense> sortedExpenses = expenses.stream()
                .sorted(Comparator.comparing(expense -> expense.getCreated().dateTime))
                .collect(Collectors.toList());

        LocalDateTime startDateTime = sortedExpenses.get(0).getCreated().dateTime;
        LocalDateTime endDateTime = sortedExpenses.get(sortedExpenses.size() - 1).getCreated().dateTime;

        DateRange dateRange = DateRange.fromClosed(startDateTime.toLocalDate(), endDateTime.toLocalDate());
        List<DateRange> timelineIntervals = dateRange.partitionByInterval(interval);


        return new FilledExpenseTimeline(interval, timelineIntervals,
                getAggregateExpensesFromSorted(timelineIntervals, sortedExpenses));
    }

    /**
     * Generates an {@code ExpenseTimeline} asynchronously using a default date interval of a month.
     */
    @Override
    public Task<ExpenseTimeline> generateAsync(List<? extends Expense> expenses) {
        return generateAsync(expenses, DateInterval.MONTH);
    }

    /**
     * Generates an {@code ExpenseTimeline} asynchronously based on the input expenses and the specified date interval.
     * @param expenses Input expenses.
     * @param interval Specified date interval.
     * @return A {@code Task} wrapping the representation of the expenses as a timeline.
     */
    public Task<ExpenseTimeline> generateAsync(List<? extends Expense> expenses, DateInterval interval) {
        Task<ExpenseTimeline> expenseTimelineTask = new Task<>() {
            @Override
            protected ExpenseTimeline call() {
                List<? extends Expense> copy = new ArrayList<>(expenses);
                return generate(copy, interval);
            }
        };
        Thread thread = new Thread(expenseTimelineTask);
        thread.setDaemon(true);
        thread.start();
        return expenseTimelineTask;
    }

    /**
     * Preconditions: {@code ranges} and {@code expenses} are both sorted chronologically.
     */
    private List<List<Expense>> getAggregateExpensesFromSorted(List<DateRange> ranges, List<Expense> expenses) {
        List<List<Expense>> aggregateExpenses = new ArrayList<>();
        List<Expense> listView = expenses;

        for (var range : ranges) {
            List<Expense> aggregateExpense = extractExpensesInDateRange(listView, range);

            aggregateExpenses.add(aggregateExpense);
            listView = listView.subList(aggregateExpense.size(), listView.size());
        }

        return aggregateExpenses;
    }

    /**
     * Returns the sublist of expenses from the that occur within the specified date range. The input expense list must
     * be sorted chronologically.
     * @param expenses Chronologically sorted expense list.
     * @param range Desired date range for output list to be contained within.
     * @return List of expenses that occur within specified date range.
     */
    private List<Expense> extractExpensesInDateRange(List<Expense> expenses, DateRange range) {
        return expenses.stream()
                        .takeWhile(expense -> range.contains(expense.getCreated().dateTime.toLocalDate()))
                        .collect(Collectors.toList());
    }
}
