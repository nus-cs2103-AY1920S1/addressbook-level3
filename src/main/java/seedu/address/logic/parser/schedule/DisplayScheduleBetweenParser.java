/*
@@author shihaoyap
 */

package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_SCHEDULE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_SCHEDULE_START;

import java.util.stream.Stream;

import seedu.address.logic.commands.schedule.DisplayScheduleBetweenCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeyDateRangePredicate;
import seedu.address.model.event.EventDate;

/**
 * Parses input arguments and creates a new DisplayScheduleBetweenCommand object
 */
public class DisplayScheduleBetweenParser implements Parser<DisplayScheduleBetweenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayScheduleBetweenCommand
     * and returns a DisplayScheduleBetweenCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayScheduleBetweenCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_SCHEDULE_START, PREFIX_EVENT_SCHEDULE_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_SCHEDULE_START, PREFIX_EVENT_SCHEDULE_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayScheduleBetweenCommand.MESSAGE_USAGE));
        }

        EventDate startDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_SCHEDULE_START).get());
        EventDate endDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_SCHEDULE_END).get());

        if (startDate.isAfter(endDate)) {
            throw new ParseException(
                    String.format(MESSAGE_DATE_START_AFTER_END, startDate, endDate));
        }

        return new DisplayScheduleBetweenCommand(
                new EventContainsKeyDateRangePredicate(startDate, endDate));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
