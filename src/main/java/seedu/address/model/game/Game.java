package seedu.address.model.game;

import seedu.address.commons.core.index.Index;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.card.Card;

public class Game {

    private WordBank wordBank;
    private Index cardIndex;
    public static final int CORRECT_GUESS = 1;
    public static final int WRONG_GUESS = 0;

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
