package seedu.billboard.model.statistics;

import java.util.List;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Expense;

/**
 * Interface for generating statistics.
 */
public interface Statistics {

    /**
     * Generates an expense timeline based on the given list of expenses, with a default date interval of a month.
     *
     * @param expenses Input expenses list.
     * @return An expense timeline.
     */
    ExpenseTimeline generateExpenseTimeline(List<? extends Expense> expenses);

    /**
     * Generates an expense timeline based on the given list of expenses, and the specified date interval
     *
     * @param expenses Input expenses.
     * @param interval Specified date interval.
     * @return An expense timeline.
     */
    ExpenseTimeline generateExpenseTimeline(List<? extends Expense> expenses, DateInterval interval);
}
