package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_PERIOD;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.CalendarEntry;

public class EventCommand extends Command {
    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the calendar. "
            + "Parameters: "
            + PREFIX_CALENDAR_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_DATETIME + "ENDING DATETIME] "
            + "[" + PREFIX_TIME_PERIOD + "TIME PERIOD]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CALENDAR_DESCRIPTION + "Appointment "
            + PREFIX_DATETIME + "5/4/2020 09:00 "
            + PREFIX_DATETIME + "5/4/2020 11:00 "
            + PREFIX_TIME_PERIOD + "1h30m";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECORD = "This event already exists in the calendar";

    private final CalendarEntry toAdd;

    /**
     * Creates an EventCommand to add the specified {@code Event}
     */
    public EventCommand(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        toAdd = calendarEntry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        /*
        if (model.hasRecord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.addRecord(toAdd);
        */
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventCommand // instanceof handles nulls
                && toAdd.equals(((EventCommand) other).toAdd));
    }
}
