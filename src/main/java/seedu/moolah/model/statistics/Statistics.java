package seedu.moolah.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.moolah.logic.commands.statistics.StatsCommand;
import seedu.moolah.logic.commands.statistics.StatsCompareCommand;
import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;



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
     * @param command Command word provided by the user
     * @param date1 First date input given by the user
     * @param date2 Second date input given by the user
     * @param primaryBudget The primary budget whose statistics is taken
     */
    public static Statistics calculateStats(String command,
                                            Timestamp date1, Timestamp date2,
                                            Budget primaryBudget, boolean isBudgetMode) {
        requireNonNull(command);
        requireNonNull(primaryBudget);

        List<Category> validCategories = Category.getValidCategories();
        switch (command) {
        case StatsCommand.COMMAND_WORD:
            return PieChartStatistics.run(validCategories, date1, date2, primaryBudget);
        case StatsCompareCommand.COMMAND_WORD:
            return TabularStatistics.run(validCategories, date1, date2, primaryBudget);
        case StatsTrendCommand.COMMAND_WORD:
            return TrendStatistics.run(validCategories, date1, date2, primaryBudget, isBudgetMode);
        default:
            return null;
        }
    }

    void setTitle(String title) {
        this.title = title;
    }

}
