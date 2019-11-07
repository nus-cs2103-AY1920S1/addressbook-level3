package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.Prefix.arePrefixesPresent;

import java.util.Set;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME,
                        PREFIX_EVENT_VENUE, PREFIX_EVENT_MANPOWER_NEEDED,
                        PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_VENUE,
                PREFIX_EVENT_MANPOWER_NEEDED, PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());
        EventVenue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_EVENT_VENUE).get());
        EventManpowerNeeded manpowerNeeded = ParserUtil.parseManpowerNeeded(
                argMultimap.getValue(PREFIX_EVENT_MANPOWER_NEEDED).get());
        EventDate start = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
        EventDate end = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_END_DATE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event event = new Event(name, venue, manpowerNeeded, start, end, tagList);

        return new AddEventCommand(event);
    }
}
