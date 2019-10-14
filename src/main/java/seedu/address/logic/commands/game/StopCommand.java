package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GameCommand;
import seedu.address.model.Model;

/**
 * Class that represents skipping over a word while Game is running.
 */
public class StopCommand extends GameCommand {
    public static final String COMMAND_WORD = "stop";
    private static final String MESSAGE_STOPPED = "Current Game has been stopped!";

    public StopCommand() {

    }

    @Override
    public CommandResult execute(Model model) {
        model.getGame().forceStop();
        return new CommandResult(MESSAGE_STOPPED);
    }
}
