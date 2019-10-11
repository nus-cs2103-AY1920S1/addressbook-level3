package seedu.address.logic.commands.trips;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.trip.Trip;

/**
 * Command that enters the itienrary page of the trip.
 */
public class EnterTripCommand extends Command {
    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the main landing page of the trip.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_TRIP_SUCCESS = " Welcome to your trip! %1$s";

    private final Index indexToEnter;

    public EnterTripCommand(Index indexToEnter) {
        this.indexToEnter = indexToEnter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Trip> lastShownList = model.getFilteredTripList();

        if (indexToEnter.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Trip tripToEnter = lastShownList.get(indexToEnter.getZeroBased());
        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ITINERARY)
                .withNewTrip(tripToEnter));

        return new CommandResult(String.format(MESSAGE_ENTER_TRIP_SUCCESS, tripToEnter), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterTripCommand;
    }
}
