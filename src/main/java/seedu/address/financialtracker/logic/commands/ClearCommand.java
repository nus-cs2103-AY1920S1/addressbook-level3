package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Clears all your expenses in the current list.
 */
public class ClearCommand extends Command<Model> {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": clears all your expenses in this list.";
    public static final String MESSAGE_SUCCESS = "Expense list cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearExpenseList();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
