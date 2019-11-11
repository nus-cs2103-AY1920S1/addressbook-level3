package seedu.address.logic.commands.gamecommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;

/**
 * Class that represents skipping over a word while Game is running.
 */
public class SkipCommand extends GameCommand {
    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_GAME_OVER = "GAME OVER!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.gameIsOver()) {
            throw new CommandException(MESSAGE_NO_ACTIVE_GAME);
        }

        Game game = model.getGame();

        /** Used for statistics purposes. */
        Card curCard = game.getCurrCard();

        /** Skip over to next Card in the game. */
        game.moveToNextCard();

        /** Check if game is over now. */
        String msg = game.isOver()
                ? MESSAGE_GAME_OVER
                : game.getCurrQuestion();
        return new SkipCommandResult(curCard, msg, game.isOver());
    }
}
