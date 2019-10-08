package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command to create a new Activity.
 */

public class ActivityCommand extends Command {
    public static final String COMMAND_WORD="activity";

    public CommandResult execute(Model model) {
        return new CommandResult("Hello from CreateActivity");
    }
}
