package seedu.address.logic.commands.bookings.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingNotFoundException;

/**
 * Placeholder.
 */
public class DoneEditBookingsCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited booking information.";

    public static final String MESSAGE_CREATE_BOOKING_SUCCESS = "Created Booking: %1$s";
    public static final String MESSAGE_EDIT_BOOKING_SUCCESS = "Edited Booking: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditBookingsFieldCommand.EditBookingsDescriptor editBookingsDescriptor = model.getPageStatus()
                .getEditBookingsDescriptor();
        Booking bookingToEdit = model.getPageStatus().getBooking();
        Booking bookingToAdd;

        if (editBookingsDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {

            if (bookingToEdit == null) {
                //buildBooking() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                bookingToAdd = editBookingsDescriptor.buildBooking();

                model.getPageStatus().getTrip().getBookingList().add(bookingToAdd);
            } else {
                //edit the current "selected" expenditure
                bookingToAdd = editBookingsDescriptor.buildExpenditure(bookingToEdit);
                model.getPageStatus().getTrip().getBookingList().set(bookingToEdit, bookingToAdd);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditEventDescriptor()
                    .withNewPageType(PageType.EXPENSE_MANAGER)
                    .withResetExpense());

            return new CommandResult(String.format(MESSAGE_EDIT_BOOKING_SUCCESS, bookingToAdd), true);
        } catch (NullPointerException | BookingNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditBookingsCommand;
    }

}
