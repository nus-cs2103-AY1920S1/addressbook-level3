//@@author dalsontws

package seedu.address.model.flashcard.exceptions;

/**
 * Signals that the operation will result in
 * duplicate flashcards (Flashcards are considered duplicates
 * if they have the same identity).
 */
public class NoBadFlashCardException extends RuntimeException {
    public NoBadFlashCardException() {
        super("There are no bad flashcards for this date.");
    }
}
