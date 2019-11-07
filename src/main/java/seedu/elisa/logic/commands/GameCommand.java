package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Command class for Game.
 */
public class GameCommand extends Command {

    public static final String COMMAND_WORD = "game";
    public static final String MESSAGE_SUCCESS = "Seems like you are really bored. Lets play a game!";

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new GameCommandResult(MESSAGE_SUCCESS);
    }
}
