package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Command class for Game.
 */
public class GameCommand extends Command {

    public static final String COMMAND_WORD = "game";
    public static final String MESSAGE_SUCCESS = "Seems like you are really bored. Lets play a game!";
    public static final String MESSAGE_USAGE = "game H / game E";

    private String diff;

    public GameCommand(String diff) {
        this.diff = diff;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        switch(diff.toLowerCase()) {
        case ("hard"):
        case ("h"):
            return new GameHardCommandResult(MESSAGE_SUCCESS);
        case ("easy"):
        case ("e"):
            return new GameCommandResult(MESSAGE_SUCCESS);
        default:
            throw new IllegalArgumentException(MESSAGE_USAGE);
        }
    }
}
