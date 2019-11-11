package seedu.system.model.session.exceptions;

/**
 * Handles any cases where the user tries to make in-competition session commands when there is no ongoing session.
 */
public class NoOngoingSessionException extends RuntimeException {

    public NoOngoingSessionException() {
        super("There is no ongoing session currently. Please start a new session.");
    }
}
