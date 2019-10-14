package seedu.address.model.game;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.card.Card;
import seedu.address.model.card.Word;

/**
 * Represents a user's guess during a Game session.
 */
public class Guess {

    // User's input guessString cannot be changed once assigned.
    private final String guessString;

    public Guess(String guessString) {
        requireAllNonNull(guessString);
        this.guessString = guessString;
    }

    /**
     * Returns true if the {@code word} matches the {@code guessString}.
     */
    public boolean matches(Word word) {
        return guessString.toLowerCase().equals(word.value.toLowerCase());
    }

    @Override
    public String toString() {
        return guessString;
    }
}
