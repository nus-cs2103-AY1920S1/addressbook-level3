package seedu.billboard.model.statistics;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Expense;

import java.util.List;

/**
 * Stateless class to generate statistics. Every method is a pure function, taking in a list of expenses and other
 * specified options and returning an object representing the desired statistic, with no side effects.
 */
public class StatisticsGenerator implements Statistics {
    @Override
    public ExpenseTimeline generateExpenseTimeline(List<Expense> expenses) {
        return generateExpenseTimeline(expenses, DateInterval.MONTH);
    }

    @Override
    public ExpenseTimeline generateExpenseTimeline(List<Expense> expenses, DateInterval interval) {
        return null;
    }
}
