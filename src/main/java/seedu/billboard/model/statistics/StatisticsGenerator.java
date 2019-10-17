package seedu.billboard.model.statistics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.util.CollectionUtil;
import seedu.billboard.model.expense.Expense;

/**
 * Stateless class to generate statistics. Every method is a pure function, taking in a list of expenses and other
 * specified options and returning an object representing the desired statistic, with no side effects.
 */
public class StatisticsGenerator implements Statistics {
    @Override
    public ExpenseTimeline generateExpenseTimeline(List<? extends Expense> expenses) {
        return generateExpenseTimeline(expenses, DateInterval.MONTH);
    }

    @Override
    public ExpenseTimeline generateExpenseTimeline(List<? extends Expense> expenses, DateInterval interval) {
        if (!CollectionUtil.checkNonEmpty(expenses)) {
            return new EmptyExpenseTimeline();
        }

        List<Expense> sortedExpenses = expenses.stream()
                .sorted(Comparator.comparing(expense -> expense.getCreated().dateTime))
                .collect(Collectors.toList());

        LocalDateTime startDateTime = sortedExpenses.get(0).getCreated().dateTime;
        LocalDateTime endDateTime = sortedExpenses.get(sortedExpenses.size() - 1).getCreated().dateTime;

        DateRange dateRange = new DateRange(startDateTime.toLocalDate(), endDateTime.toLocalDate());


        return new FilledExpenseTimeline(dateRange, interval,
                getAggregateExpensesFromSorted(dateRange.partitionByInterval(interval), sortedExpenses));
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
