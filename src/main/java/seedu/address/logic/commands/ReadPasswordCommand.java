package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reads a password identified using it's displayed index from the password book.
 */
public class ReadPasswordCommand extends Command {
    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Reads and copies to clipboard the password identified by "
            + "the index number used in the display list. \n"
            + "Parameters: INDEX (must be positive integer), /e(optional) to see your password";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
