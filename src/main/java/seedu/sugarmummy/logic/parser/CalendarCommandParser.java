package seedu.sugarmummy.logic.parser;

import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_DAY;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_WEEK;

import java.util.stream.Stream;

import seedu.sugarmummy.logic.commands.calendar.CalendarCommand;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.time.YearMonth;

/**
 * Parses input arguments and creates a new CalendarCommand object
 */
public class CalendarCommandParser implements Parser<CalendarCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarCommand and returns an CalendarCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_YEAR_MONTH, PREFIX_YEAR_MONTH_DAY, PREFIX_YEAR_MONTH_WEEK);

        YearMonth yearMonth;

        if (argMultimap.getValue(PREFIX_YEAR_MONTH_DAY).isPresent()) {
            return new CalendarCommand(ParserUtil.parseYearMonthDay(argMultimap.getValue(PREFIX_YEAR_MONTH_DAY).get()),
                    false);
        }
        if (argMultimap.getValue(PREFIX_YEAR_MONTH_WEEK).isPresent()) {
            return new CalendarCommand(ParserUtil.parseYearMonthDay(argMultimap.getValue(PREFIX_YEAR_MONTH_WEEK).get()),
                    true);
        }
        if (argMultimap.getValue(PREFIX_YEAR_MONTH).isPresent()) {
            yearMonth = ParserUtil.parseYearMonth(argMultimap.getValue(PREFIX_YEAR_MONTH).get());
        } else {
            yearMonth = new YearMonth(java.time.YearMonth.now());
        }

        return new CalendarCommand(yearMonth);
    }
}
