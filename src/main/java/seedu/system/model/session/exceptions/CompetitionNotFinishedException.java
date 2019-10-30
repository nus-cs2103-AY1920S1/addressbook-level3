package seedu.system.model.session.exceptions;

/**
 * Handles the case where the user tries to end the competition session when not all lifters have made their attempts.
 */
public class CompetitionNotFinishedException extends RuntimeException {

    public CompetitionNotFinishedException() {
        super("There are lifters who have not made their attempts. The competition cannot end.");
    }
}
