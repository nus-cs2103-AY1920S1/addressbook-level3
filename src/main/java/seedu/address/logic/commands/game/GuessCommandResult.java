package seedu.address.logic.commands.game;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;
import seedu.address.statistics.GameDataPoint;

public class GuessCommandResult extends GameCommandResult {

    public static final String MESSAGE_WRONG_GUESS = "Guess is WRONG!";
    public static final String MESSAGE_CORRECT_GUESS = "Guess is CORRECT!";

    private final Guess guess;

    public GuessCommandResult(Guess guess, Card card, String additionalMsg, boolean isFinishedGame) {
        super(card,
                (guess.matches(card.getWord()) ? MESSAGE_CORRECT_GUESS : MESSAGE_WRONG_GUESS)
                + "\n"
                +  additionalMsg,
                isFinishedGame,
                false);
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
