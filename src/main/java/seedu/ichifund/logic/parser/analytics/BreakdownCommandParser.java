package seedu.ichifund.logic.parser.analytics;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Optional;

import seedu.ichifund.logic.commands.analytics.BreakdownCommand;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

/**
 * Parses input arguments and creates a new BreakdownCommand object
 */
public class BreakdownCommandParser implements Parser<BreakdownCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BreakdownCommand
     * and returns an BreakdownCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BreakdownCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BreakdownCommand.MESSAGE_USAGE));
        }
        Month month = null;
        Year year = null;

        if (argMultimap.getValue(PREFIX_MONTH).isPresent()) {
            month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        }

        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        }

        Optional<Month> optionalMonth = Optional.ofNullable(month);
        Optional<Year> optionalYear = Optional.ofNullable(year);

        return new BreakdownCommand(optionalMonth, optionalYear);
    }
}
