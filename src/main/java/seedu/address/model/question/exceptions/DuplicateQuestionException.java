package seedu.address.model.question.exceptions;

/**
 * Signals that the operation will result in questions with duplicate bodies. (Questions are considered duplicates
 * if they have the same body.)
 */
public class DuplicateQuestionException extends RuntimeException {
    public DuplicateQuestionException() {
        super("Operation would result in duplicate questions");
    }
}
