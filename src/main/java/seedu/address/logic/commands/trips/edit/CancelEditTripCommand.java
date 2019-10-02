package seedu.address.logic.commands.trips.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.trip.Trip;
import seedu.address.model.itinerary.trip.exceptions.ClashingTripException;
import seedu.address.model.itinerary.trip.exceptions.TripNotFoundException;
import seedu.address.ui.Ui;
import seedu.address.ui.trips.TripsPage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class CancelEditTripCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new trip ";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the trip!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the trip: %1$s";

    public CancelEditTripCommand() { }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireAllNonNull(model, ui);
        Trip currentlyEditingTrip = model.getPageStatus().getTrip();
        model.setPageStatus(model.getPageStatus()
                .withNewEditTripDescriptor(null)
                .withNewPageType(PageType.TRIP_MANAGER)
                .withNewTrip(null));
        ui.switchWindow(TripsPage.class);

        if (currentlyEditingTrip == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingTrip));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditTripCommand;
    }
}
