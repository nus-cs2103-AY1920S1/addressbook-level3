package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GameCommand;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;

/**
 * Class that represents skipping over a word while Game is running.
 */
public class SkipCommand extends GameCommand {
    public static final String COMMAND_WORD = "skip";

    public SkipCommand() {

    }

    @Override
    public CommandResult execute(Model model) {
        if (model.getGame() == null) {
            return new CommandResult(MESSAGE_NO_ACTIVE_GAME);
        }

        Game game = model.getGame();

        Card curCard = game.getCurrCard();

        // Skip current card, move to next card.
        game.moveToNextCard();

        String msg = game.isOver()
                ? "GAME OVER!"
                : game.getCurrQuestion();

        return new SkipCommandResult(curCard, msg, game.isOver());
    }
}
