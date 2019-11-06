package seedu.deliverymans.model.deliveryman.exceptions;

import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * Signals that the operation is unable to retrieve any available deliveryman.
 */
public class UnableToDeleteDeliveringDeliverymanException extends CommandException {
    public UnableToDeleteDeliveringDeliverymanException() {
        super("You cannot delete a deliveryman who is currently delivering an order. ");
    }
}
