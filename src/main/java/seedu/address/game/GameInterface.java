package seedu.address.game;

import seedu.address.model.card.Hint;

/**
 * Interface for a game.
 */
public interface GameInterface {
    void startGame();
    void endGame();
    void skipCard();
    boolean guess(String input);
    Hint getHint();
}
