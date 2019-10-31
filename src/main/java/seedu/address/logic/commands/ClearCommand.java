package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.expense.Expense;

/**
 * Clears the address book.
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
        String viewState = model.getViewState();

        if (viewState.equals("default expenselist")) {
            model.setExpenseList(new ExpenseList());
            return new CommandResult(MESSAGE_CLEAR_EXPENSES_SUCCESS);
        } else if (viewState.equals("expenselist inside budget")) {
            Budget lastViewedBudget = model.getLastViewedBudget();
            lastViewedBudget.setExpenseListInBudget(new ExpenseList());

            return new CommandResult(model.getExpenseListFromBudget(lastViewedBudget), null,
                String.format(MESSAGE_CLEAR_EXPENSES_IN_BUDGET_SUCCESS, lastViewedBudget.getName()));
        } else if (viewState.equals("budgetlist")) {
            model.setBudgetList(new BudgetList());
            return new CommandResult(null, model.getFilteredBudgetList(), MESSAGE_CLEAR_BUDGETS_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_CLEAR_ERROR);
        }
    }
}
