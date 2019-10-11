package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a app command
 */
public abstract class AppCommand extends Command {
    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        if (mode != ModeEnum.APP) {
            throw new CommandException("You're not in App mode!");
        }
        return ModeEnum.APP;
    }
}
