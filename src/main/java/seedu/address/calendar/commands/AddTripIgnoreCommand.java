package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Trip;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddTripIgnoreCommand extends AddTripCommand {
    public AddTripIgnoreCommand(Trip trip) {
        super(trip);
    }

    public CommandResult execute(Calendar calendar) throws CommandException, ClashException {
        try {
            calendar.addIgnoreClash(trip);
        } catch (DuplicateEventException e) {
            throw new CommandException(e.getMessage());
        }

        String formattedFeedback = String.format(MESSAGE_ADD_SUCCESS, trip.toString());
        return new CommandResult(formattedFeedback);
    }
}
