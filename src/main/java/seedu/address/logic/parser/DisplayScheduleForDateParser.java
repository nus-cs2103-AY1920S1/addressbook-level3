package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.DisplayScheduleCommand;
import seedu.address.logic.commands.DisplayScheduleForDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeyDatePredicate;
import seedu.address.model.event.EventDate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class DisplayScheduleForDateParser implements Parser<DisplayScheduleForDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns a FindEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayScheduleForDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayScheduleForDateCommand.MESSAGE_USAGE));
        }

        EventDate start = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        return new DisplayScheduleForDateCommand(new EventContainsKeyDatePredicate(start.date));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
