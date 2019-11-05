package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.addcommand.AddScheduleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddScheduleCommand object
 */
public class AddScheduleCommandParser implements Parser<AddScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddScheduleCommand
     * and returns an AddScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_VENUE, PREFIX_TAG, PREFIX_ALLOW);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_VENUE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE), pe);
        }

        Calendar calendar = ParserUtil.parseCalendar(argMultimap.getValue(PREFIX_DATE).get(),
                argMultimap.getValue(PREFIX_TIME).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Schedule schedule = new Schedule(UUID.randomUUID(), calendar, venue, tagList);

        boolean canClash = false;
        if (arePrefixesPresent(argMultimap, PREFIX_ALLOW)) {
            canClash = true;
        }

        return new AddScheduleCommand(schedule, index, canClash);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
