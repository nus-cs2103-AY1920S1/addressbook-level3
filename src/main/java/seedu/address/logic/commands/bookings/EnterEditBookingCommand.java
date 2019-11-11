package seedu.address.logic.commands.bookings;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.bookings.edit.EditBookingsFieldCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.booking.Booking;

/**
 * Placeholder.
 */
public class EnterEditBookingCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the booking information editing screen\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_EDIT_BOOKING_SUCCESS = " Welcome to your booking! %1$s";

    private final Index indexToEdit;

    public EnterEditBookingCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes EnterDayCommand has already been called
        List<Booking> lastShownList = model.getPageStatus().getTrip().getBookingList().internalList;

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Booking bookingToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditBookingsFieldCommand.EditBookingsDescriptor editBookingsDescriptor =
                new EditBookingsFieldCommand.EditBookingsDescriptor(bookingToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_BOOKINGS)
                .withNewBooking(bookingToEdit)
                .withNewEditBookingsDescriptor(editBookingsDescriptor));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_BOOKING_SUCCESS, bookingToEdit), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditBookingCommand;
    }
}
