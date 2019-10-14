package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GameCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;
import seedu.address.model.game.Guess;

/**
 * Make a guess.
 */
public class GuessCommand extends GameCommand {
    public static final String COMMAND_WORD = "guess";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Makes a guess for current flashcard with "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Guess inputGuess;

    public GuessCommand(Guess inputGuess) {
        this.inputGuess = inputGuess;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Game game = model.getGame();
        String msg = "";
        /*
        if (game.isOver()) {
            String exceptionText = ("The Game has ended."
                    + "\n"
                    + "Type 'start' to try again!");
        }*/

        if (model.getGame() == null) {
            return new CommandResult(MESSAGE_NO_ACTIVE_GAME);
        }

        Card guessedCard = game.getCurrCard();
        game.moveToNextCard();

        if (game.isOver()) {
            msg = "GAME OVER!";
        } else {
            msg = game.getCurrQuestion();
        }

        return new GuessCommandResult(inputGuess, guessedCard, msg, game.isOver());
    }
}
