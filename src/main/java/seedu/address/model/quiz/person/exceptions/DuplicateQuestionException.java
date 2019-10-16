package seedu.address.model.quiz.person.exceptions;

/**
 * Signals that the operation will result in duplicate Questions (Questions are considered duplicates
 * if they have the same identity).
 */
public class DuplicateQuestionException extends RuntimeException {
    public DuplicateQuestionException() {
        super("Operation would result in duplicate questions");
    }
}
