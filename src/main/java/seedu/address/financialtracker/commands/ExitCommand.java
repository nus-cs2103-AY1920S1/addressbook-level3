package seedu.address.financialtracker.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * add financial expenses command for Financial Tracker.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    private static final String MESSAGE_FAIL = "Exits fail";

    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_FAIL, false, true);
    }
}
