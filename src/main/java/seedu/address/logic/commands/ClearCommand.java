package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;

/**
 * Clears all Expenses/Budgets.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CLEAR_EXPENSES_SUCCESS = "Expense list has been cleared!";
    public static final String MESSAGE_CLEAR_BUDGETS_SUCCESS = "Budget list has been cleared!";
    public static final String MESSAGE_CLEAR_EXPENSES_IN_BUDGET_SUCCESS = "Budget: %1$s has been cleared!";

    public static final String MESSAGE_CLEAR_ERROR = "An error occurred while trying to clear list";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewState viewState = model.getViewState();

        if (viewState.equals(ViewState.DEFAULT_EXPENSELIST)) {
            model.setExpenseList(new ExpenseList());
            return new CommandResult(model.getFilteredExpenseList(), null,
                null, MESSAGE_CLEAR_EXPENSES_SUCCESS);
        } else if (viewState.equals(ViewState.EXPENSELIST_IN_BUDGET)) {
            Budget lastViewedBudget = model.getLastViewedBudget();
            lastViewedBudget.setExpenseListInBudget(new ExpenseList());

            return new CommandResult(model.getExpenseListFromBudget(lastViewedBudget), null, null,
                String.format(MESSAGE_CLEAR_EXPENSES_IN_BUDGET_SUCCESS, lastViewedBudget.getName()));
        } else if (viewState.equals(ViewState.BUDGETLIST)) {
            model.setBudgetList(new BudgetList());
            return new CommandResult(null, model.getFilteredBudgetList(),
                null, MESSAGE_CLEAR_BUDGETS_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_CLEAR_ERROR);
        }
    }
}
