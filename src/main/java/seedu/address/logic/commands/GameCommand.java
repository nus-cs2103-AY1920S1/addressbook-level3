package seedu.address.logic.commands;

import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a game command todo give a more descriptive comment
 */
public abstract class GameCommand extends Command {

    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        return ModeEnum.GAME;
    }
}
