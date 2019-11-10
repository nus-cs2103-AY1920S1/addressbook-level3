package seedu.address.financialtracker.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Deletes a financial expense, command for Financial Tracker.
 */
public class DeleteFinCommand extends Command<Model> {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS = "Your expense has been deleted";
    public static final String MESSAGE_USAGE = ": delete an expense.\n"
            + "e.g. delete [index]";
    public static final String MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX = "The expense index provided is invalid";
    private final Index index;

    /**
     * Creates a DeleteFinCommand to delete an expense at index.
     * @param index of expense to delete
     */
    public DeleteFinCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Expense> expenses = model.getExpenseList();

        if (index.getZeroBased() >= expenses.size()) {
            throw new CommandException(MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }
        model.deleteExpense(index.getOneBased());
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFinCommand // instanceof handles nulls
                && index.equals(((DeleteFinCommand) other).index));
    }
}
