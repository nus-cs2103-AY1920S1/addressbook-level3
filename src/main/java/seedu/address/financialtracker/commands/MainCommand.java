package seedu.address.financialtracker.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * add financial expenses command for Financial Tracker.
 */
public class MainCommand extends Command {

    public static final String COMMAND_WORD = "main";

    public CommandResult execute(Model model) {
        return new CommandResult(COMMAND_WORD, false, false, true);
    }
}
