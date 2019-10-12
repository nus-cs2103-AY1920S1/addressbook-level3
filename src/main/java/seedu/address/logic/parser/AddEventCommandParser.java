package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_HOURS_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventEndDate;
import seedu.address.model.event.EventHoursNeeded;
import seedu.address.model.event.EventId;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventStartDate;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME,
                        PREFIX_EVENT_VENUE, PREFIX_EVENT_HOURS_NEEDED, PREFIX_EVENT_MANPOWER_NEEDED,
                        PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_VENUE,
                PREFIX_EVENT_HOURS_NEEDED, PREFIX_EVENT_MANPOWER_NEEDED,
                PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        EventId id = ParserUtil.parseEventId();
        EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());
        EventVenue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_EVENT_VENUE).get());
        EventHoursNeeded hoursNeeded = ParserUtil.parseHoursNeeded(
                argMultimap.getValue(PREFIX_EVENT_HOURS_NEEDED).get());
        EventManpowerNeeded manpowerNeeded = ParserUtil.parseManpowerNeeded(
                argMultimap.getValue(PREFIX_EVENT_MANPOWER_NEEDED).get());
        EventStartDate start = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
        EventEndDate end = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_EVENT_END_DATE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event event = new Event(id, name, venue, hoursNeeded,
                manpowerNeeded, start, end, tagList);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
