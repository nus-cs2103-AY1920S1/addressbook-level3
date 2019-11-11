package seedu.system.model.attempt.exceptions;

/**
 * Signals when an an attempted Attempt is being updated again.
 */
public class AttemptHasBeenAttemptedException extends RuntimeException {

    public AttemptHasBeenAttemptedException() {
        super("The attempt has already been updated and recorded.\n"
                + "Enter 'next' to get the next attempt and lifter.\n");
    }
}
