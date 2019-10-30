package seedu.address.model.session.exceptions;

import seedu.address.logic.commands.session.AttemptLiftedCommand;

/**
 * Handles the case where the user tries to call the next lifter before
 * the previous lifter has finished and recorded his attempt.
 */
public class PreviousAttemptNotDoneException extends RuntimeException {

    public PreviousAttemptNotDoneException() {
        super("The next lifter is not ready to be called until"
                + " the current lifter has completed and recorded the attempt.\n"
                + "To record attempt, enter " + AttemptLiftedCommand.MESSAGE_USAGE);
    }
}
