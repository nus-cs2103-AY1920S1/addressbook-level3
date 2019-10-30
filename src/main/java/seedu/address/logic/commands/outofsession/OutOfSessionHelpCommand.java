package seedu.address.logic.commands.outofsession;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.InSessionCommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class OutOfSessionHelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
