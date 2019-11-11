package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.ui.budget.BudgetListPanel;

/**
 * Deletes a budget identified using its displayed index from BudgetListPanel.
 */
public class DeleteBudgetByIndexCommand extends UndoableCommand {
    public static final String COMMAND_WORD = GenericCommandWord.DELETE + CommandGroup.BUDGET + "-id";
    public static final String COMMAND_DESCRIPTION = "Delete budget on index %1$d";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the budget identified by the index number used in the displayed budget list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted Budget:\n %1$s";

    private final Index targetIndex;

    /**
     * Creates an DeleteBudgetByIndexCommand to delete the budget with the specified {@code targetIndex}.
     */
    public DeleteBudgetByIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, targetIndex.getOneBased());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        }

        if (lastShownList.size() == 1) {
            throw new CommandException(Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
        }

        Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (budgetToDelete.isDefaultBudget()) {
            throw new CommandException(Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgetList();

        Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBudget(budgetToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete),
                BudgetListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBudgetByIndexCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBudgetByIndexCommand) other).targetIndex)); // state check
    }

}
