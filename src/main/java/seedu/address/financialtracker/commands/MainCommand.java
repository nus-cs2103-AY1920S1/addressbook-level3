package seedu.address.financialtracker.commands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.financialtracker.Model.Model;

/**
 * Command to switch to Main window.
 */
public class MainCommand extends Command {

    public static final String COMMAND_WORD = "main";

    public CommandResult execute(Model model) {
        return new CommandResult(COMMAND_WORD, false, false, true);
    }
}
