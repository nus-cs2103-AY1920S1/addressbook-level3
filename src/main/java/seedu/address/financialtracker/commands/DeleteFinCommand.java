package seedu.address.financialtracker.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * add financial expenses command for Financial Tracker.
 */
public class DeleteFinCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_FAIL = "Unknown error, your expenses are not deleted.";

    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_FAIL, false, false);
    }
}
