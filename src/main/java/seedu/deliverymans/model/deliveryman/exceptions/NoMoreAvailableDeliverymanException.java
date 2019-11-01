package seedu.deliverymans.model.deliveryman.exceptions;

import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * Signals that the operation is unable to retrieve any available deliveryman.
 */
public class NoMoreAvailableDeliverymanException extends CommandException{
    public NoMoreAvailableDeliverymanException() {
        super("There is currently no deliveryman who is available to be assigned orders. ");
    }
}
