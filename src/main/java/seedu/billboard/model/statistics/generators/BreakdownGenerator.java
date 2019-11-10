package seedu.billboard.model.statistics.generators;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseBreakdown;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.formats.FilledExpenseBreakdown;

/**
 * Stateless class to generate a breakdown of expenses. Methods here are guaranteed to have no external
 * side effects, or depend on external state.
 */
public class BreakdownGenerator implements StatisticsGenerator<ExpenseBreakdown> {

    /**
     * Generates an expense breakdown based on the given expense grouping grouped by the default of tags.
     */
    @Override
    public ExpenseBreakdown generate(List<? extends Expense> expenses) {
        return generate(expenses, ExpenseGrouping.TAG);
    }

    /**
     * Generates an expense breakdown based on the given expense grouping.
     * @param expenses Input expenses to be broken down.
     * @param grouping Grouping that expenses should be grouped by.
     * @return Expense breakdown representing the broken down expenses.
     */
    public ExpenseBreakdown generate(List<? extends Expense> expenses, ExpenseGrouping grouping) {
        return new FilledExpenseBreakdown(grouping.getGroupingFunction().group(expenses));
    }

    /**
     * Asynchronously generates an expense breakdown based on the given expense grouping grouped by the default of tags.
     */
    @Override
    public Task<ExpenseBreakdown> generateAsync(List<? extends Expense> expenses) {
        return generateAsync(expenses, ExpenseGrouping.TAG);
    }

    /**
     * Generates an expense breakdown asynchronously based on the given expense grouping.
     * @param expenses Input expenses to be broken down.
     * @param grouping Grouping that expenses should be grouped by.
     * @return Task wrapper around an expense breakdown representing the broken down expenses.
     */
    public Task<ExpenseBreakdown> generateAsync(List<? extends Expense> expenses, ExpenseGrouping grouping) {
        Task<ExpenseBreakdown> expenseBreakdownTask = new Task<>() {
            @Override
            protected ExpenseBreakdown call() {
                List<? extends Expense> copy = new ArrayList<>(expenses);
                return generate(copy, grouping);
            }
        };
        Thread thread = new Thread(expenseBreakdownTask);
        thread.setDaemon(true);
        thread.start();
        return expenseBreakdownTask;
    }
}
