package seedu.address.model.game;

import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;

/**
 * Represents a game. todo give a more descriptive comment
 */
public class Game {

    public static final int CORRECT_GUESS = 1;
    public static final int WRONG_GUESS = 0;

    private WordBank wordBank;
    private Index cardIndex;

    public Game(WordBank wordBank) {
        this.wordBank = wordBank;
        this.cardIndex = Index.fromZeroBased(0);
    }

    private Card getCurrCard() {
        return wordBank.getCard(cardIndex);
    }

    public String showCurrQuestion() {
        return getCurrCard().getMeaning().toString();
    }

    public int makeGuess(Guess inputGuess) {
        return inputGuess.matches(getCurrCard().getWord()) ? CORRECT_GUESS : WRONG_GUESS;
    }

    private Index getCurrIndex() {
        return cardIndex;
    }

    public boolean isOver() {
        return getCurrIndex().getZeroBased() >= wordBank.size();
    }

    public void moveToNextCard() {
        cardIndex.increment();
    }
}
