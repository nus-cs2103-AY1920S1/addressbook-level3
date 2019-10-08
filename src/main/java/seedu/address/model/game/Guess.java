package seedu.address.model.game;

import seedu.address.model.card.Word;

/**
 * Represents a user's guess.
 */
public class Guess {

    private final String guessString;

    public Guess(String guessString) {
        this.guessString = guessString;
    }

    public String getGuessString() {
        return this.guessString;
    }

    /**
     * Returns true if the {@code word} matches the {@code guessString}.
     */
    public boolean matches(Word word) {
        //  System.out.println("Guess String is: " + guessString );
        //  System.out.println("Answer String is: " + answer.getAnswerString());
        return guessString.toLowerCase().equals(word.value.toLowerCase());
    }
}
