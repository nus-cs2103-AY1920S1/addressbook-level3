package seedu.guilttrip.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Expense;

/**
 * Deletes an expense entry identified using its displayed index from guiltTrip.
 */
public class DeleteExpenseCommand extends Command {

    public static final String COMMAND_WORD = "deleteExpense";
    public static final String COMMAND_WORD_SHORT = "deleteExp";

    public static final String ONE_LINER_DESC = COMMAND_WORD + " / " + COMMAND_WORD_SHORT
            + " : Deletes the expense identified by the index number used in the displayed expense list.\n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Expense: %1$s";

    private final Index targetIndex;

    public DeleteExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenses();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Expense expenseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExpense(expenseToDelete);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, expenseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExpenseCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteExpenseCommand) other).targetIndex)); // state check
    }
}
