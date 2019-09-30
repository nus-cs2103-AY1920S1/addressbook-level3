package seedu.address.logic.commands.trips.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.itinerary.trip.Trip;
import seedu.address.model.itinerary.trip.exceptions.ClashingTripException;
import seedu.address.model.itinerary.trip.exceptions.TripNotFoundException;
import seedu.address.model.pagestatus.PageType;
import seedu.address.ui.trips.TripsPage;
import seedu.address.ui.Ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Constructs a command that attempts to commit the current changes in the edit trip page.
 */
public class DoneEditTripCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited trip information ";

    public static final String MESSAGE_CREATE_TRIP_SUCCESS = "Created Trip: %1$s";
    public static final String MESSAGE_EDIT_TRIP_SUCCESS = "Edited Trip: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to must be provided!";
    public static final String MESSAGE_CLASHING_TRIP = "This trip clashes with one of your other trips!";

    public DoneEditTripCommand() { }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireAllNonNull(model, ui);
        EditTripFieldCommand.EditTripDescriptor editTripDescriptor = model.getPageStatus().getEditTripDescriptor();
        Trip tripToEdit = model.getPageStatus().getTrip();
        Trip tripToAdd;

        try {
            if (tripToEdit == null) {
                //add new trip
                tripToAdd = editTripDescriptor.buildTrip();
                model.addTrip(tripToAdd);
                ui.switchWindow(TripsPage.class);

                return new CommandResult(String.format(MESSAGE_CREATE_TRIP_SUCCESS, tripToAdd));
            }

            tripToAdd = editTripDescriptor.buildTrip(tripToEdit);
            model.setTrip(tripToEdit, tripToAdd);
            model.setPageStatus(
                    model.getPageStatus()
                            .withNewEditTripDescriptor(null)
                            .withNewPageType(PageType.TRIP_MANAGER));
            ui.switchWindow(TripsPage.class);

            return new CommandResult(String.format(MESSAGE_EDIT_TRIP_SUCCESS, tripToAdd));
        } catch (TripNotFoundException ex) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        } catch (ClashingTripException ex) {
            return new CommandResult(MESSAGE_CLASHING_TRIP);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditTripCommand;
    }

}
