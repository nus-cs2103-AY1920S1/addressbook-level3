package seedu.address.model.game;

import seedu.address.model.deck.Deck;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;

public class Game {

    private Deck deck;
    private int cardIndex = 0;
    public static final int CORRECT_GUESS = 1;
    public static final int WRONG_GUESS = 0;

    public Game(Deck deck) {
        this.deck = deck;
    }

    private Flashcard getCurrCard() {
        return deck.get(cardIndex);
    }

    public String showCurrQuestion() {
        return getCurrCard().getQuestion().toString();
    }

    public int makeGuess(Guess inputGuess) {
        return inputGuess.matches(getCurrCard().getAnswer()) ? CORRECT_GUESS : WRONG_GUESS;
    }

    private int getCurrIndex() {
        return cardIndex;
    }

    public boolean isOver() {
        return getCurrIndex() >= deck.size();
    }

    public void moveToNextCard() {
        cardIndex++;
    }


}
