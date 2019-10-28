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
 * Views a budget identified using it's displayed index from the budget list.
 */
public class ViewBudgetCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Views the budget identified by the index number used in the displayed list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_BUDGET_SUCCESS = "You are viewing the budget: %1$s";

    public static final String MESSAGE_VIEW_ERROR = "You have to be viewing the budget list "
        + "first before you can view a budget";

    private final Index targetIndex;

    public ViewBudgetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String viewState = model.getViewState();

        if (viewState.equals("listbudgets")) {
            List<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
            }

            Budget budgetToView = lastShownList.get(targetIndex.getZeroBased());
            model.viewBudget(budgetToView);
            return new CommandResult(String.format(MESSAGE_VIEW_BUDGET_SUCCESS, budgetToView));
        } else {
            throw new CommandException(MESSAGE_VIEW_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ViewBudgetCommand // instanceof handles nulls
            && targetIndex.equals(((ViewBudgetCommand) other).targetIndex)); // state check
    }
}
