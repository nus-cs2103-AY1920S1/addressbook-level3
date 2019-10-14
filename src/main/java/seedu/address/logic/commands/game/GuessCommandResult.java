package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;

public class GuessCommandResult extends CommandResult {

    public static final String MESSAGE_WRONG_GUESS = "Guess is WRONG!";
    public static final String MESSAGE_CORRECT_GUESS = "Guess is CORRECT!";

    private final Card card;
    private final Guess guess;

    public GuessCommandResult(Guess guess, Card card, String additionalMsg) {
        super((guess.matches(card.getWord()) ? MESSAGE_CORRECT_GUESS : MESSAGE_WRONG_GUESS)
                + "\n"
                +  additionalMsg,
                true);
        this.card = card;
        this.guess = guess;
    }

    public Card getCard() {
        return card;
    }

    public Guess getGuess() {
        return guess;
    }
}
