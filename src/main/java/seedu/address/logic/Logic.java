package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.budget.Budget;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.expense.Expense;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ExpenseList.
     *
     * @see Model#getExpenseList()
     */
    ReadOnlyExpenseList getExpenseList();

    /**
     * Returns an unmodifiable view of the list of expenses
     */
    ObservableList<Expense> getExpenses();

    /**
     * Returns an unmodifiable view of the filtered list of expenses
     */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Returns an unmodifiable view of the filtered list of all expenses including those in budgets
     */
    //    ObservableList<Expense> getFilteredFullExpenseList();

    /**
     * Returns the user prefs' expense list file path.
     */
    Path getExpenseListFilePath();

    /**
     * Returns the BudgetList.
     *
     * @see Model#getBudgetList()
     */
    ReadOnlyBudgetList getBudgetList();

    /**
     * Returns an unmodifiable view of the filtered list of budgets
     */
    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Returns the user prefs' budget list file path.
     */
    Path getBudgetListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ObservableList<Expense> updateExpenses();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();
}
