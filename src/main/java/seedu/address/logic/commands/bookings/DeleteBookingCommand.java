package seedu.address.logic.commands.bookings;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Placeholder.
 */
public class DeleteBookingCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a booking from booking manager.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_BOOKING_FAILURE = "Failed to delete your booking, "
            + "the booking you are trying to remove is likely to be associated with an event, please go to"
            + " the corresponding event to delete the booking";
    public static final String MESSAGE_DELETE_BOOKING_SUCCESS = "Deleted your booking : %1$s!";

    private final Index indexToDelete;

    public DeleteBookingCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes enter trip has been called first
        List<Booking> lastShownList = model.getPageStatus().getTrip().getBookingList().internalList;

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        // References preserved by PageStatus
        Booking bookingToDeleted = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.getPageStatus().getTrip().getBookingList().remove(bookingToDeleted);
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_DELETE_BOOKING_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDeleted));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteBookingCommand;
    }

}
