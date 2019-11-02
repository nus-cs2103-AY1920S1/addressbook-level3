package seedu.address.financialtracker.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * delete a financial expense, command for Financial Tracker.
 */
public class DeleteFinCommand extends Command<Model> {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS = "Your expense has been deleted";
    public static final String MESSAGE_USAGE = ": delete an expense.\n"
            + "e.g. delete [index]";
    private final Index index;

    /**
     * Creates a DeleteFinCommand to delete an expense at index.
     * @param index of expense to delete
     */
    public DeleteFinCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) {
        model.deleteExpense(index.getOneBased());
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
