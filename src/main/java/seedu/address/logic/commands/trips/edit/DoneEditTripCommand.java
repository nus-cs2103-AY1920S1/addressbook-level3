package seedu.address.logic.commands.trips.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;
import seedu.address.model.appstatus.PageType;

import static java.util.Objects.requireNonNull;

/**
 * Constructs a command that attempts to commit the current changes in the edit trip page.
 */
public class DoneEditTripCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited trip information.";

    public static final String MESSAGE_CREATE_TRIP_SUCCESS = "Created Trip: %1$s";
    public static final String MESSAGE_EDIT_TRIP_SUCCESS = "Edited Trip: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_CLASHING_TRIP = "This trip clashes with one of your other trips!";

    public DoneEditTripCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditTripFieldCommand.EditTripDescriptor editTripDescriptor = model.getPageStatus().getEditTripDescriptor();
        Trip tripToEdit = model.getPageStatus().getTrip();
        Trip tripToAdd;

        if (editTripDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            if (tripToEdit == null) {
                //buildTrip() requires all fields to be non null, failing which
                //NullPointerException is caught below
                tripToAdd = editTripDescriptor.buildTrip();
                model.addTrip(tripToAdd);
            } else {
                //edit the current "selected" trip
                tripToAdd = editTripDescriptor.buildTrip(tripToEdit);
                model.setTrip(tripToEdit, tripToAdd);
            }

            model.setPageStatus(model.getPageStatus()
                    .withNewEditTripDescriptor(null)
                    .withNewPageType(PageType.TRIP_MANAGER)
                    .withNewTrip(null));

            return new CommandResult(String.format(MESSAGE_EDIT_TRIP_SUCCESS, tripToAdd), true);
        } catch (NullPointerException | TripNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        } catch (ClashingTripException ex) {
            throw new CommandException(MESSAGE_CLASHING_TRIP);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditTripCommand;
    }

}
