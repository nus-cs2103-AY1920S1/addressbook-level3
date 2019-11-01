package seedu.deliverymans.model.deliveryman.exceptions;

import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * Signals that the operation is not executable (The status of the deliveryman must be AVAILABLE or UNAVAILABLE).
 */
public class InvalidStatusChangeException extends CommandException {
    public InvalidStatusChangeException() {
        super("Current status of deliveryman must be either AVAILABLE or UNAVAILABLE in order to be switched. ");
    }
}
