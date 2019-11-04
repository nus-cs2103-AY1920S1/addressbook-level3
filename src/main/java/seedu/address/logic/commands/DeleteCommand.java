package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;

/**
 * Deletes an expense identified using it's displayed index from the expense list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the item identified by the index number used in the displayed list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted Expense: %1$s";
    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted Budget: %1$s";
    public static final String MESSAGE_DELETE_ERROR = "An error occurred while trying to delete the item";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String viewState = model.getViewState();

        if (viewState.equals("default expenselist")) {
            List<Expense> lastShownList = model.getFilteredExpenseList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
            }

            Expense expenseToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteExpense(expenseToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete));
        } else if (viewState.equals("budgetlist")) {
            List<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
            }

            Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteBudget(budgetToDelete);
            return new CommandResult(null, model.getFilteredBudgetList(), null,
                String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete));
        } else if (viewState.equals("expenselist inside budget")) {
            Budget viewingBudget = model.getLastViewedBudget();
            List<Expense> expenseListInsideBudget = viewingBudget.getObservableExpenseList();

            if (targetIndex.getZeroBased() >= expenseListInsideBudget.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
            }

            Expense expenseToDelete = expenseListInsideBudget.get(targetIndex.getZeroBased());
            viewingBudget.deleteExpenseInBudget(expenseToDelete);
            return new CommandResult(model.getExpenseListFromBudget(viewingBudget), null, null,
                String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete));
        } else {
            throw new CommandException(MESSAGE_DELETE_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
