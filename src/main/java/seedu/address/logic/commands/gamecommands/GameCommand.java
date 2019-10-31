// @@author sreesubbash
package seedu.address.logic.commands.gamecommands;

import seedu.address.logic.commands.Command;

/**
 * Represents a command executed on game.
 */
public abstract class GameCommand extends Command {
    protected static final String MESSAGE_NO_ACTIVE_GAME = "There is no active game!";
}
