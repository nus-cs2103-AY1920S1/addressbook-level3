package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command to create a new Activity.
 */

public class ActivityCommand extends Command {
    public static final String COMMAND_WORD = "activity";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new Activity.\n"
            + "Parameters: \n"
            + "Example: Activity ";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Activity command not implemented yet";

    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
