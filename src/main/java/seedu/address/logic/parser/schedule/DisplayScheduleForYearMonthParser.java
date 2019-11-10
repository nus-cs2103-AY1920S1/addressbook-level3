/*
@@author shihaoyap
 */

package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;

import java.time.YearMonth;
import java.util.stream.Stream;

import seedu.address.logic.commands.schedule.DisplayScheduleForYearMonthCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeyYearMonthPredicate;

/**
 * Parses input arguments and creates a new DisplayScheduleForYearMonthCommand object
 */
public class DisplayScheduleForYearMonthParser implements Parser<DisplayScheduleForYearMonthCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayScheduleForYearMonthCommand
     * and returns a DisplayScheduleForYearMonthCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayScheduleForYearMonthCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_YEAR_MONTH);

        if (!arePrefixesPresent(argMultimap, PREFIX_YEAR_MONTH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayScheduleForYearMonthCommand.MESSAGE_USAGE));
        }

        YearMonth yearMonth = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_YEAR_MONTH).get());

        return new DisplayScheduleForYearMonthCommand(new EventContainsKeyYearMonthPredicate(yearMonth));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
