package seedu.address.logic.commands.gameCommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GameCommand;
import seedu.address.model.Model;

/**
 * Class that represents forcibly stopping a Game session that is running.
 */
public class StopCommand extends GameCommand {
    public static final String COMMAND_WORD = "stop";
    private static final String MESSAGE_STOPPED = "Current Game has been forcibly stopped!";

    /**
     * Executes the StopCommand to forcibly terminate the current game session.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult to be passed back into the UI.
     */
    @Override
    public CommandResult execute(Model model) {
        model.getGame().forceStop();
        // No collection of statistics when game is stopped.
        return new CommandResult(MESSAGE_STOPPED);
    }
}
