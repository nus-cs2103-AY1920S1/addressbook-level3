package seedu.address.model.flashcard.exceptions;

/**
 * Signals that the operation will result in duplicate Flashcards (Flashcards are considered duplicates if they have
 * the same fields).
 */
public class DuplicateFlashcardException extends RuntimeException {
    public DuplicateFlashcardException() {
        super("Operation would result in duplicate flashcards");
    }
}
