package seedu.billboard.model.statistics.generators;

import java.util.List;

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
}
