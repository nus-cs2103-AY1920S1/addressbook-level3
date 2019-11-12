package seedu.address.model.flashcard.exceptions;

/**
 * Signals that the operation will result in duplicate Flashcards. Specifically, the flashcards are considered
 * duplicates as they have the same Title field.
 */
public class DuplicateFlashcardTitleException extends RuntimeException {
    public DuplicateFlashcardTitleException() {
        super("Operation would result in duplicate flashcards as the flashcard has the same title as an existing "
                + "flashcard!");
    }
}
