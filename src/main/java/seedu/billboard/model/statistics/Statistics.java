package seedu.billboard.model.statistics;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Expense;

import java.util.List;

/**
 * Interface for generating statistics.
 */
public interface Statistics {

    /**
     * Generates an expense timeline based on the given list of expenses, with a default date interval of a month.
     * @param expenses Input expenses.
     * @return An expense timeline.
     */
    ExpenseTimeline generateExpenseTimeline(List<Expense> expenses);

    /**
     * Generates an expense timeline based on the given list of expenses, and the specified date interval
     * @param expenses Input expenses.
     * @param interval Specified date interval.
     * @return An expense timeline.
     */
    ExpenseTimeline generateExpenseTimeline(List<Expense> expenses, DateInterval interval);
}
