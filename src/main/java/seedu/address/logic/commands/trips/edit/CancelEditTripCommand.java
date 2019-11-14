package seedu.address.logic.commands.trips.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.trip.Trip;

/**
 * Command that cancels editing the trip, bringing the user back to the trip manager screen.
 */
public class CancelEditTripCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new trip.";

    private static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the trip!";
    private static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the trip: %1$s";

    public CancelEditTripCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Trip currentlyEditingTrip = model.getPageStatus().getTrip();
        model.setPageStatus(model.getPageStatus()
                .withNewEditTripDescriptor(null)
                .withNewPageType(PageType.TRIP_MANAGER)
                .withResetTrip());

        if (currentlyEditingTrip == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingTrip), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditTripCommand;
    }
}
