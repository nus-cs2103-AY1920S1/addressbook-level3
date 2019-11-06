package seedu.moolah.logic.parser.statistics;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;

import java.util.Collections;
import java.util.List;

import seedu.moolah.logic.commands.statistics.StatsCompareCommand;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.expense.Timestamp;

/**
 * Parses input arguments and creates a new StatsCompareCommand object
 */
public class StatsCompareCommandParser implements Parser<StatsCompareCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(
            List.of(PREFIX_FIRST_START_DATE, PREFIX_SECOND_START_DATE)
    );

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of());

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCompareCommand
     * and returns an StatsCompareCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCompareCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FIRST_START_DATE, PREFIX_SECOND_START_DATE);
        if (!argMultimap.arePrefixesPresent(PREFIX_FIRST_START_DATE, PREFIX_SECOND_START_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCompareCommand.MESSAGE_USAGE));
        }

        if (argMultimap.hasRepeatedPrefixes(PREFIX_FIRST_START_DATE, PREFIX_SECOND_START_DATE)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }


        Timestamp startDate1 = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_FIRST_START_DATE).get());
        Timestamp startDate2 = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_SECOND_START_DATE).get());

        return new StatsCompareCommand(startDate1, startDate2);
    }

}


