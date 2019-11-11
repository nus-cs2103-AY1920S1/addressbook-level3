package seedu.sugarmummy.logic.parser.calendar;

import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_DAY;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_WEEK;

import seedu.sugarmummy.logic.commands.calendar.CalendarCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.ParserUtil;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.time.YearMonthDay;

/**
 * Parses input arguments and creates a new CalendarCommand object
 */
public class CalendarCommandParser implements Parser<CalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarCommand and returns an CalendarCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_YEAR_MONTH, PREFIX_YEAR_MONTH_DAY, PREFIX_YEAR_MONTH_WEEK);

        if (argMultimap.getValue(PREFIX_YEAR_MONTH_DAY).isPresent()) {
            String yearMonthDayStr = argMultimap.getValue(PREFIX_YEAR_MONTH_DAY).get();
            if (yearMonthDayStr.isEmpty()) {
                return new CalendarCommand(YearMonthDay.getToday(), false);
            } else {
                return new CalendarCommand(ParserUtil.parseYearMonthDay(yearMonthDayStr), false);
            }
        }

        if (argMultimap.getValue(PREFIX_YEAR_MONTH_WEEK).isPresent()) {
            String yearMonthDayStr = argMultimap.getValue(PREFIX_YEAR_MONTH_WEEK).get();
            if (yearMonthDayStr.isEmpty()) {
                return new CalendarCommand(YearMonthDay.getToday(), true);
            } else {
                return new CalendarCommand(ParserUtil.parseYearMonthDay(yearMonthDayStr), true);
            }
        }

        if (argMultimap.getValue(PREFIX_YEAR_MONTH).isPresent()) {
            String yearMonthStr = argMultimap.getValue(PREFIX_YEAR_MONTH).get();
            if (yearMonthStr.isEmpty()) {
                return new CalendarCommand(YearMonthDay.getToday().getYearMonth());
            } else {
                return new CalendarCommand(ParserUtil.parseYearMonth(yearMonthStr));
            }
        }

        return new CalendarCommand();
    }
}
