package seedu.address.logic.commands.sidebar;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Command that brings the user to the TripManager Page via the navbar.
 * Clears the trip, day, and event in model.
 */
public class EnterTripManagerCommand extends Command {
    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the landing page of TravelPal.";

    public static final String MESSAGE_SUCCESS = "Entered the landing screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setPageStatus(model.getPageStatus()
                .withResetEvent()
                .withResetDay()
                .withResetTrip()
                .withNewPageType(PageType.TRIP_MANAGER));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof EnterTripManagerCommand;
    }
}
