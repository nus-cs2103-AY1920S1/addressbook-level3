package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_DURATION;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.TimeDuration;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Event;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;

/**
 * Parses input arguments and creates a new EventCommand object
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventCommand
     * and returns an EventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CALENDAR_DESCRIPTION, PREFIX_DATETIME, PREFIX_TIME_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CALENDAR_DESCRIPTION, PREFIX_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_CALENDAR_DESCRIPTION).get());
        List<String> dateTimes = argMultimap.getAllValues(PREFIX_DATETIME);
        DateTime dateTime = ParserUtil.parseDateTime(dateTimes.get(0));
        Event event = new Event(description, dateTime);

        if (dateTimes.size() > 1) {
            DateTime endingDateTime = ParserUtil.parseDateTime(dateTimes.get(1));
            event.setEndingDateTime(endingDateTime);
        }

        if (argMultimap.getValue(PREFIX_TIME_DURATION).isPresent()) {
            TimeDuration timeDuration = ParserUtil.parseTimeDuration(argMultimap.getValue(PREFIX_TIME_DURATION).get());
            DateTime reminderDateTime = dateTime.minus(timeDuration);
            Description reminderDescription = new Description(getAutoReminderDescription(event, timeDuration));
            Reminder autoReminder = new Reminder(reminderDescription, reminderDateTime, Repetition.Once);
            event.setAutoReminder(autoReminder);
        }

        return new EventCommand(event);
    }

    private String getAutoReminderDescription(Event event, TimeDuration timeDuration) {
        return "Event: " + event.getDescription() + "in " + timeDuration;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

