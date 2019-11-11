package seedu.address.logic.commands.gamecommands;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;
import seedu.address.statistics.GameDataPoint;

/**
 * Represents the command result returned by {@code GuessCommand}.
 * This class is needed to pass some info to the {@code GameManager} to populate the {@code GameStatistics}.
 */
public class GuessCommandResult extends GameCommandResult {

    public static final String MESSAGE_WRONG_GUESS = "Guess is WRONG!";
    public static final String MESSAGE_CORRECT_GUESS = "Guess is CORRECT!";

    private final Guess guess;

    public GuessCommandResult(Guess guess, Card card, String additionalMsg, boolean isFinishedGame) {
        super(card, (
                        guess.matches(card.getWord()) ? MESSAGE_CORRECT_GUESS : MESSAGE_WRONG_GUESS)
                + "\n"
                + additionalMsg,
                isFinishedGame);
        this.guess = guess;
    }

    public Guess getGuess() {
        return guess;
    }

    @Override
    public GameDataPoint getGameDataPoint(long millisElapsed) {
        return GameDataPoint.createGuessData(guess, millisElapsed);
    }
}
