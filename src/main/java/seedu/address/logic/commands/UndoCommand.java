package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes last entered command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Hello from undo");
    }
}
