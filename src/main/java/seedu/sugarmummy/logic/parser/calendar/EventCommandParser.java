package seedu.sugarmummy.logic.parser.calendar;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_EVENT_ENDING_TIME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_TIME_DURATION;

import java.util.List;
import java.util.stream.Stream;

import seedu.sugarmummy.logic.commands.calendar.EventCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.ParserUtil;
import seedu.sugarmummy.logic.parser.Prefix;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.calendar.Description;
import seedu.sugarmummy.model.calendar.Event;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.calendar.Repetition;
import seedu.sugarmummy.model.time.DateTime;
import seedu.sugarmummy.model.time.TimeDuration;

/**
 * Parses input arguments and creates a new EventCommand object
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EventCommand and returns an EventCommand
     * object for execution.
     *
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
            if (endingDateTime.isBeforeDateTime(dateTime)) {
                throw new ParseException(MESSAGE_INVALID_EVENT_ENDING_TIME);
            } else if (!endingDateTime.equals(dateTime)) {
                event.setEndingDateTime(endingDateTime);
            }
            //ending date time is omitted if it is equal to the starting date time
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
        return "Event: " + event.getDescription() + " in " + timeDuration;
    }

}
