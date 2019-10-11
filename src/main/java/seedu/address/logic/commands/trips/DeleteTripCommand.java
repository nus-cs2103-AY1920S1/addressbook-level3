package seedu.address.logic.commands.trips;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.trip.Trip;

/**
 * Command that deletes a trip.
 */
public class DeleteTripCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a trip from TravelPal.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_TRIP_FAILURE = "Failed to delete your trip, "
            + "the index you specified is likely out of bounds!";
    public static final String MESSAGE_DELETE_TRIP_SUCCESS = "Deleted your trip : %1$s!";

    private final Index indexToDelete;

    public DeleteTripCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Trip> lastShownList = model.getFilteredTripList();

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Trip tripToDelete = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.deleteTrip(tripToDelete);
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_DELETE_TRIP_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TRIP_SUCCESS, tripToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteTripCommand;
    }
}
