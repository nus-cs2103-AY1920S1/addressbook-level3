package seedu.system.model.session.exceptions;

/**
 * Handles the case where a PartcipationAttempt object is created with a wrong attempt index.
 */
public class WrongAttemptIndexException extends RuntimeException {

    public WrongAttemptIndexException(int index) {
        super("Attempt Index " + index
                + " is invalid. Attempt index should be a number from 1 to 9 representing the lift and attempt number");
    }
}
