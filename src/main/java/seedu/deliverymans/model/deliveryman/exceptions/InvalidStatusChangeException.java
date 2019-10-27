package seedu.deliverymans.model.deliveryman.exceptions;

/**
 * Signals that the operation is not executable (The status of the deliveryman must be AVAILABLE of UNAVAILABLE).
 */
public class InvalidStatusChangeException extends RuntimeException {
    public InvalidStatusChangeException() {
        super("Current status of deliveryman must be either AVAILABLE or UNAVAILABLE in order to be switched. ");
    }
}