package seedu.system.model.attempt.exceptions;

/**
 * Signals that an athlete has already made 3 attempts.
 */
public class MaximumAttemptsReachedException extends RuntimeException {

    public MaximumAttemptsReachedException() {
        super("This athlete has already reached the maximum number of attempts.");
    }
}
