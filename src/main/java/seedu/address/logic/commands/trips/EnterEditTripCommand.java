package seedu.address.logic.commands.trips;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.trip.Trip;
import seedu.address.ui.Ui;
import seedu.address.ui.itinerary.DaysPage;
import seedu.address.ui.trips.EditTripPage;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class EnterEditTripCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the trip information editing screen"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_EDIT_TRIP_SUCCESS = " Welcome to your trip! %1$s";

    private final Index indexToEdit;

    public EnterEditTripCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireAllNonNull(model, ui);
        List<Trip> lastShownList = model.getFilteredTripList();

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Trip tripToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditTripFieldCommand.EditTripDescriptor editTripDescriptor =
                new EditTripFieldCommand.EditTripDescriptor(tripToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_TRIP)
                .withNewTrip(tripToEdit)
                .withNewEditTripDescriptor(editTripDescriptor));

        ui.switchWindow(EditTripPage.class);

        return new CommandResult(String.format(MESSAGE_EDIT_TRIP_SUCCESS, tripToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditTripCommand;
    }
}
