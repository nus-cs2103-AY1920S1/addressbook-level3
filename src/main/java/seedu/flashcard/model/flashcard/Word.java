package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * The word itself of the flashcard.
 * TODO: Can be replaced by the flashcard question.
 */
public class Word {

    public static final String MESSAGE_CONSTRAINTS =
            "Words should only contain alphabets, no spaces is allowed, and it should not be blank";
    public static final String VALIDATION_REGEX = "^[a-zA-Z]*$";

    public final String word;

    public Word(String word) {
        requireNonNull(word);
        checkArgument(isValidWord(word), MESSAGE_CONSTRAINTS);
        this.word = word;
    }

    /**
     * Returns true if a giving string is a valid word.
     */
    public static boolean isValidWord(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Word)
                && word.equals(((Word) other).word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
