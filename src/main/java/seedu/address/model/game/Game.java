package seedu.address.model.game;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;

/**
 * Represents a game session using Cards from a specified WordBank.
 * Guarantees: WordBank is not null, and that WordBank is not empty.
 */
public class Game {

    public static final int CORRECT_GUESS = 1;
    public static final int WRONG_GUESS = 0;

    // Current WordBank cannot be changed once assigned.
    private final WordBank wordBank;

    // Stateful field Index that updates as game progresses.
    private Index cardIndex;

    /**
     * Constructor for Game instance that takes in a WordBank.
     * WordBank must not be null.
     * @param wordBank WordBank that current Game session will run on.
     */
    public Game(WordBank wordBank) {
        requireAllNonNull(wordBank);
        this.wordBank = wordBank;
        this.cardIndex = Index.fromZeroBased(0);
    }

    /**
     * Returns current Card at the current index. Throws {@code UnsupportedOperationException}
     * if game has already ended (no more available cards).
     */
    public Card getCurrCard() throws UnsupportedOperationException {
        if (isOver()) {
            throw new UnsupportedOperationException("Game is already Over");
        }
        return wordBank.getCard(cardIndex);
    }

    /**
     * Returns meaning of current Card at the current index as a string.
     * Throws {@code UnsupportedOperationException} if game has already ended (no more available cards).
     */
    public String getCurrQuestion() throws UnsupportedOperationException {
        return getCurrCard().getMeaning().toString();
    }

    /**
     * Returns true if the user's guess is correct.
     * @param inputGuess User's input guess of the game's current card.
     * @throws UnsupportedOperationException if game has already ended.
     */
    public boolean checkGuess(Guess inputGuess) throws UnsupportedOperationException {
        return inputGuess.matches(getCurrCard().getWord());
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
