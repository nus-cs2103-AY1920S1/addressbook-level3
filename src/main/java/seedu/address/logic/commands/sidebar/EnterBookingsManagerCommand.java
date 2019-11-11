package seedu.address.logic.commands.sidebar;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Enters the bookings page of travel pal.
 */
public class EnterBookingsManagerCommand extends Command {
    public static final String COMMAND_WORD = "bookings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the booking manager page of TravelPal.";

    public static final String MESSAGE_SUCCESS = "Entered the booking manager screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setPageStatus(model.getPageStatus()
                .withResetBooking()
                .withNewPageType(PageType.BOOKINGS));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterBookingsManagerCommand;
    }
}
