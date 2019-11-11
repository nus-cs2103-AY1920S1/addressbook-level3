package seedu.ichifund.logic.commands;

import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;

/**
 * Represents an empty {@code Command} that returns a {@code CommandResult} with an empty string when executed.
 */
public class EmptyCommand extends Command {
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("");
    }
}
