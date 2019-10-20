package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a app command
 */
public abstract class AppCommand extends Command {

    @Override
    public boolean check(Model model) throws CommandException {
        return true;
    }
}
