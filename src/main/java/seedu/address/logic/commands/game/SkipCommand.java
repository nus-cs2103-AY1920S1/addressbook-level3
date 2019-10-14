package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GameCommand;
import seedu.address.model.Model;
import seedu.address.model.game.Game;

/**
 * Class that represents skipping over a word while Game is running.
 */
public class SkipCommand extends GameCommand {
    public static final String COMMAND_WORD = "skip";
    private static final String MESSAGE_SKIPPED = "Word skipped!";

    public SkipCommand() {

    }

    @Override
    public CommandResult execute(Model model) {
        if (model.getGame() == null) {
            return new CommandResult(MESSAGE_NO_ACTIVE_GAME);
        }

        Game game = model.getGame();

        // Skip current card, move to next card.
        game.moveToNextCard();

        if (!game.isOver()) {
            return new CommandResult(MESSAGE_SKIPPED
                    + "\n" + game.getCurrQuestion(), true);
        } else {
            return new CommandResult(MESSAGE_SKIPPED
                    + "\n" + "GAME IS OVER!");
        }
    }
}
