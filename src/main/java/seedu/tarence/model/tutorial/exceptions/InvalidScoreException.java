package seedu.tarence.model.tutorial.exceptions;

import seedu.tarence.model.tutorial.Assignment;

/**
 * Signals that the operation is unable to find the specified Week.
 */
public class InvalidScoreException extends RuntimeException {
    public InvalidScoreException() {
        super(Assignment.MESSAGE_CONSTRAINTS_SCORE);
    }
}
