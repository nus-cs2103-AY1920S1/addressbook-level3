package seedu.address.model.answerable.exceptions;

/**
 * Signals that the operation will result in duplicate Answerables (Answerables are considered duplicates if they have the same
 * identity).
 */
public class DuplicateAnswerableException extends RuntimeException {
    public DuplicateAnswerableException() {
        super("Operation would result in duplicate answerables");
    }
}
