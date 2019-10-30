package seedu.address.financialtracker.logic.commands;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens up a window with the commands available for financial tracker.
 */
public class HelpCommand extends Command<Model> {
    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up a window which showcase all"
            + " the commands for financial tracker.";

    public static final String MESSAGE_SUCCESS = "Currently viewing the financial tracker help window.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS, true, false);
    }
}
