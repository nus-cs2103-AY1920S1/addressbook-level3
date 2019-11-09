package seedu.flashcard.model.flashcard;

import java.util.Set;

import seedu.flashcard.model.tag.Tag;

/**
 * Short Answer MCQ flashcard. Does not contain any choices.
 */
public class ShortAnswerFlashcard extends Flashcard {

    /**
     * This initializer is used when the user is creating a new flashcard
     */
    public ShortAnswerFlashcard(Question question, Definition definitions, Set<Tag> tags, Answer answer) {
        super(question, definitions, tags, answer);
    }

    /**
     * Creates a deep copy of a short answer flashcard
     * @param toClone the flashcard to be cloned
     */
    public ShortAnswerFlashcard(ShortAnswerFlashcard toClone) {
        super(toClone);
    }

    /**
     * This initializer should only be accessed by the storage package, because this initializer ensures the
     * flashcard scores can be reloaded
     */
    public ShortAnswerFlashcard(Question question, Definition definition, Set<Tag> tags, Answer answer, Score score) {
        super(question, definition, tags, answer, score);
    }

    @Override
    public boolean isValidFlashcard() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ShortAnswerFlashcard)) {
            return false;
        }
        return super.equals(other);
    }

    @Override
    public boolean isMcq() {
        return false;
    }
}
