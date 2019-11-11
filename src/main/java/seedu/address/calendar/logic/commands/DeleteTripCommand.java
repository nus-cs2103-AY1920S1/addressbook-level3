package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Trip;
import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.NoSuchElementException;

public class DeleteTripCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "trip";
    public static final String MESSAGE_USAGE = DeleteCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the specified trip that happens on the specified date(s)"
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + CliSyntax.PREFIX_NAME + "NAME " + "\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "9 "
            + CliSyntax.PREFIX_START_MONTH + "Dec " + CliSyntax.PREFIX_NAME + "1 day at Johor";

    private Trip trip;

    public DeleteTripCommand(Trip trip) {
        this.trip = trip;
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException, NoSuchElementException {
        calendar.deleteEvent(trip);
        String formattedFeedback = String.format(MESSAGE_DELETE_SUCCESS, trip.toString());
        return new CommandResult(formattedFeedback);
    }
}
