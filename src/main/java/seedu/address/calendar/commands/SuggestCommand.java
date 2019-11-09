package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.parser.CliSyntax;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.Optional;

public class SuggestCommand extends Command<Calendar> {
    public static final String COMMAND_WORD = "suggest";
    public static final String MESSAGE_SUGGESTION_SUCCESS = "Here is a list of suggestion for you:\n%s";
    public static final String MESSAGE_SUGGESTION_FAILED = "No available block that meets your constraints.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Suggests when you can travel "
            + "during a period of time specified by the start and end date. "
            + "If you need the suggested block of time to be of a minimum number of days, please specify it.\n"
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
    private Optional<Integer> minPeriod;

    public SuggestCommand(EventQuery eventQuery, int minPeriod) {
        this.eventQuery = eventQuery;
        this.minPeriod = Optional.of(minPeriod);
    }

    public SuggestCommand(EventQuery eventQuery) {
        this.eventQuery = eventQuery;
        minPeriod = Optional.empty();
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException {
        String suggestions = minPeriod.map(n -> calendar.suggest(eventQuery, n))
                .orElse(calendar.suggest(eventQuery));
        if (suggestions.equals("")) {
            return new CommandResult(MESSAGE_SUGGESTION_FAILED);
        }
        String formattedFeedback = String.format(MESSAGE_SUGGESTION_SUCCESS, suggestions);
        return new CommandResult(formattedFeedback);
    }
}