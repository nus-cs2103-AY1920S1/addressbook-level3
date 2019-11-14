package seedu.address.logic.commands.trips;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand.EditTripDescriptor;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.trip.Trip;

/**
 * Enters the trip editing screen for the indicated trip.
 */
public class EnterEditTripCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the trip information editing screen\n"
            + "Parameters: INDEX (must be a positive integer)";

    private static final String MESSAGE_ENTER_EDIT_TRIP_SUCCESS = " Welcome to your trip! %1$s";

    private final Index indexToEdit;

    public EnterEditTripCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Trip> lastShownList = model.getFilteredTripList();

        // Set when the trip list is first displayed to the user
        SortedList currentSortedTripList = model.getPageStatus().getSortedOccurrencesList();

        int rawZeroBasedIndex = currentSortedTripList.getSourceIndex(indexToEdit.getZeroBased());

        if (rawZeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Trip tripToEdit = lastShownList.get(rawZeroBasedIndex);
        EditTripDescriptor editTripDescriptor = new EditTripDescriptor(tripToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_TRIP)
                .withNewTrip(tripToEdit)
                .withNewEditTripDescriptor(editTripDescriptor));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_TRIP_SUCCESS, tripToEdit), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditTripCommand;
    }
}
