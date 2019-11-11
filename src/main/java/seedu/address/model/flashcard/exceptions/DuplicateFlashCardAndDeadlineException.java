//@@author dalsontws

package seedu.address.model.flashcard.exceptions;

/**
 * Signals that the operation will result in
 * duplicate flashcards (Flashcards are considered duplicates
 * if they have the same identity) and duplicate
 * deadlines (considered duplicate if has same task name and
 * due date)
 */
public class DuplicateFlashCardAndDeadlineException extends RuntimeException {
    public DuplicateFlashCardAndDeadlineException() {
        super("Operation would result in duplicate flashcards and deadlines");
    }
}
