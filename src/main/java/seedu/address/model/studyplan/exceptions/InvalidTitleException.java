package seedu.address.model.studyplan.exceptions;

/**
 * Signals that the operation will result in an invalid title for a study plan.
 */

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String message) {
        super(message);
    }
}
