package seedu.ichifund.logic.commands.budget;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.budget.Budget;

/**
 * Deletes a budget identified using it's displayed index from the displayed budget list.
 */
public class DeleteBudgetCommand extends Command {

    public static final String COMMAND_WORD = "bdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the budget identified by the index number used in the displayed budget list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted Budget: %1$s";

    private final Index targetIndex;

    public DeleteBudgetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        }

        Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBudget(budgetToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBudgetCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBudgetCommand) other).targetIndex)); // state check
    }
}
