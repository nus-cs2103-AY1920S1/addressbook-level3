package seedu.billboard.model.statistics;

import java.util.List;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Expense;

/**
 * Interface for generating statistics.
 */
public interface StatisticsGenerator<T> {

    /**
     * Generates statistics based on the given list of expenses.
     *
     * @param expenses Input expenses list.
     * @return A statistic representation T.
     */
    T generate(List<? extends Expense> expenses);

    /**
     * Generates an expense timeline based on the given list of expenses, and the specified date interval.
     *
     * @param expenses Input expenses.
     * @param interval Specified date interval.
     * @return A statistic representation T.
     */
    T generate(List<? extends Expense> expenses, DateInterval interval);
}
