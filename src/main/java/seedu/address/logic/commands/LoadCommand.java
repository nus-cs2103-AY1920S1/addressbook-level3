package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a game command todo give a more descriptive comment
 */
public abstract class LoadCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean check(Model model) throws CommandException {
        return true;
    }
}
