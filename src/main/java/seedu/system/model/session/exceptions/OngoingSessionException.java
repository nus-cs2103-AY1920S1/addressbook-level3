package seedu.system.model.session.exceptions;

/**
 * Handles the case where the user tries to start a session when there is already an ongoing session.
 * Prompts the user to end the previous session before starting a new one.
 */
public class OngoingSessionException extends RuntimeException {

    public OngoingSessionException() {
        super("There is an ongoing competition. Please end the session before starting a new one.");
    }
}
