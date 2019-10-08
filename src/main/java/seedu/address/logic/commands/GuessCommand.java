package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.game.Game;
import seedu.address.model.Model;
import seedu.address.model.game.Guess;

public class GuessCommand extends GameCommand {
    public static final String COMMAND_WORD = "guess";
    public static final String MESSAGE_WRONG_GUESS = "Guess is WRONG!";
    public static final String MESSAGE_CORRECT_GUESS = "Guess is CORRECT!";
    public static final String MESSAGE_NO_ACTIVE_GAME = "There is no active game!";
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
        if (model.getGame() == null) {
           return new CommandResult(MESSAGE_NO_ACTIVE_GAME);
        } else {
            int guessResult = model.getGame().makeGuess(inputGuess);

            if (guessResult == Game.CORRECT_GUESS) {
                return new CommandResult(MESSAGE_CORRECT_GUESS, true);
            } else {
                //guessResult == Game.WRONG_GUESS
                return new CommandResult(MESSAGE_WRONG_GUESS, true);
            }
        }
    }
}
