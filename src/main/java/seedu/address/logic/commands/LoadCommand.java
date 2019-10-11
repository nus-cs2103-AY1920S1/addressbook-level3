package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a game command todo give a more descriptive comment
 */
public abstract class LoadCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        if (mode != ModeEnum.LOAD) {
            throw new CommandException("Load word bank first!");
        }
        return ModeEnum.GAME;
    }
}
