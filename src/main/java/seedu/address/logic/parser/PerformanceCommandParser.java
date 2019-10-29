package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_TIMING;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PerformanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.Timing;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class PerformanceCommandParser implements Parser<PerformanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PerformanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT, PREFIX_DATE, PREFIX_TIMING);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT, PREFIX_DATE, PREFIX_TIMING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PerformanceCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PerformanceCommand.MESSAGE_USAGE), pe);
        }

        String event = ParserUtil.parseEvent(argMultimap.getValue(PREFIX_EVENT).get());
        AthletickDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Timing timing = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).get());

        return new PerformanceCommand(index, event, date, timing);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
