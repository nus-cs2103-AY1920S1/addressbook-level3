package seedu.address.model.booking;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Exception thrown when a trip is not found in an operation.
 */
public class BookingNotFoundException extends CommandException {

    public BookingNotFoundException(String message) {
        super(message);
    }
}
