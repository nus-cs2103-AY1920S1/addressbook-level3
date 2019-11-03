package seedu.billboard.model.statistics.generators;

import java.util.List;

import javafx.concurrent.Task;
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
     * Generates statistics based on the given list of expenses, asynchronously.
     * @param expenses Input expenses list.
     * @return A {@code Task} wrapping the statistics representation T.
     */
    Task<T> generateAsync(List<? extends Expense> expenses);
}
