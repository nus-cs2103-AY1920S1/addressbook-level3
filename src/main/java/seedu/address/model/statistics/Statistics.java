package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.statistics.StatsCommand;
import seedu.address.logic.commands.statistics.StatsCompareCommand;
import seedu.address.logic.commands.statistics.StatsTrendCommand;
import seedu.address.model.budget.BudgetPeriod;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;



/**
 * Represents the Statistics class in MooLah.
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS_END_DATE = "Start date must be before end date.";

    private ObservableList<Expense> expenses;

    private final List<Category> validCategories;

    private int categorySize;

    private String title;

    /**
     * Creates a Statistics object
     * @param expenses A list of expenses in the current budget
     * @param validCategories A list of tags used among all expenses
     */
    public Statistics(ObservableList<Expense> expenses, List<Category> validCategories) {
        requireNonNull(validCategories);
        requireNonNull(expenses);
        this.expenses = expenses;
        this.validCategories = validCategories;
        this.categorySize = validCategories.size();
    }

    /**
     * Returns the lists of all expenses in the current budget
     */
    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    public String getTitle() {
        return title;
    }

    List<Category> getValidCategories() {
        return validCategories;
    }

    int getCategorySize() {
        return categorySize;
    }

    /**
     * The main handler method of the Statistics model object to identify what kind of Statistics has to be done
     * with each command word
     * @param expenses List of expenses
     * @param command Command word provided by the user
     * @param date1 First date input given by the user
     * @param date2 Second date input given by the user
     * @param period Period of time that may be relevant to the operation
     */
    public static Statistics calculateStats(ObservableList<Expense> expenses, String command,
                                            Timestamp date1, Timestamp date2,
                                            BudgetPeriod period, boolean isBudgetMode) {
        requireNonNull(expenses);
        List<Category> validCategories = Category.getValidCategories();
        switch (command) {
        case StatsCommand.COMMAND_WORD:
            return PieChartStatistics.run(expenses, validCategories, date1, date2);
        case StatsCompareCommand.COMMAND_WORD:
            return TabularStatistics.run(expenses, validCategories, date1, date2, period);
        case StatsTrendCommand.COMMAND_WORD:
            return TrendStatistics.run(expenses, validCategories, date1, date2, period, isBudgetMode);
        default:
            return null;
        }
    }

    void setTitle(String title) {
        this.title = title;
    }

}
