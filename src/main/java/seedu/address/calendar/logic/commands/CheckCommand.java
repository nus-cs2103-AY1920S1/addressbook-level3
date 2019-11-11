package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

public class CheckCommand extends Command<Calendar> {
    public static final String COMMAND_WORD = "check";
    public static final String MESSAGE_AVAILABLE = "You are available.";
    public static final String MESSAGE_UNAVAILABLE = "You aren't available.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks whether you are free to travel "
            + "during the specified period of time (for the entire period).\n"
            + "Parameters: "
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY]  "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + "[" + CliSyntax.PREFIX_PERIOD + "MIN NO. OF DAYS]" + "\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "5 " + CliSyntax.PREFIX_START_MONTH
            + "Dec " + CliSyntax.PREFIX_END_DAY + "31 " + CliSyntax.PREFIX_PERIOD + "5";

    private EventQuery eventQuery;

    public CheckCommand(EventQuery eventQuery) {
        this.eventQuery = eventQuery;
    }

    @Override
    public CommandResult execute(Calendar calendar) {
        boolean isAvailable = calendar.isAvailable(eventQuery);

        if (isAvailable) {
            return new CommandResult(MESSAGE_AVAILABLE);
        } else {
            return new CommandResult(MESSAGE_UNAVAILABLE);
        }
    }
}
