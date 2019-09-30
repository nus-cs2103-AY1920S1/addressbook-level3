package seedu.address.logic.commands.trips;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.itinerary.trip.Trip;
import seedu.address.model.pagestatus.PageType;
import seedu.address.ui.Ui;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

public class DeleteTripCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a trip from TravelPal. "
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_TRIP_SUCCESS = "Deleted your trip : %1$s!";

    private final Index indexToDelete;

    public DeleteTripCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireAllNonNull(model, ui);
        List<Trip> lastShownList = model.getFilteredTripList();

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Trip tripToDelete = lastShownList.get(indexToDelete.getZeroBased());
        model.deleteTrip(tripToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_TRIP_SUCCESS, tripToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteTripCommand;
    }
}
