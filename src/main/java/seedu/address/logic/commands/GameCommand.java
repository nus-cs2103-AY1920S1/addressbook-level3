package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a game command
 */
public abstract class GameCommand extends Command {

    protected static final String MESSAGE_NO_ACTIVE_GAME = "There is no active game!";

    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        return ModeEnum.GAME;
    }
}
