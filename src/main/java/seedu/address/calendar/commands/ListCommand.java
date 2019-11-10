package seedu.address.calendar.commands;

import java.util.Optional;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.parser.CliSyntax;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Command which allows user to list his/her events. If no date is specified, all events will be listed.
 * If not, only events from the relevant time period will be listed.
 */
public class ListCommand extends Command<Calendar> {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_LIST_FAILED = "There are no events found";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists your events. "
            + "If no dates are specified, all events will be listed.\n"
            + "Parameters: "
            + "[" + CliSyntax.PREFIX_START_DAY + "START DAY] "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY]  "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + "[" + CliSyntax.PREFIX_PERIOD + "MIN NO. OF DAYS]" + "\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "5 " + CliSyntax.PREFIX_START_MONTH
            + "Dec " + CliSyntax.PREFIX_END_DAY + "31 " + CliSyntax.PREFIX_PERIOD + "5";

    private Optional<EventQuery> eventQuery;

    /**
     * Creates a {@code ListCommand} that will list all events that happen during the specified event query.
     *
     * @param eventQuery The specified event query
     */
    public ListCommand(EventQuery eventQuery) {
        this.eventQuery = Optional.of(eventQuery);
    }

    /**
     * Creates a {@code ListCommand} that will list all events from the user's calendar.
     */
    public ListCommand() {
        this.eventQuery = Optional.empty();
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException {
        String relevantEvents;

        if (eventQuery.isEmpty()) {
            relevantEvents = calendar.listAll();
        } else {
            relevantEvents = calendar.list(eventQuery.get());
        }

        if (relevantEvents.equals("")) {
            return new CommandResult(MESSAGE_LIST_FAILED, false, false, false, true);
        }

        String formattedFeedback = String.format(relevantEvents);
        return new CommandResult(formattedFeedback, false, false, false, true);
    }
}
