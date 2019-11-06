package seedu.moolah.logic.parser.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import seedu.moolah.logic.commands.budget.SwitchPeriodCommand;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.expense.Timestamp;

/**
 * Parses input arguments and creates a new SwitchPeriodCommand object.
 */
public class SwitchPeriodCommandParser implements Parser<SwitchPeriodCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_TIMESTAMP
    ));
    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of());

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchPeriodCommand
     * and returns a SwitchPeriodCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchPeriodCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIMESTAMP);

        if (!arePrefixesPresent(argMultimap, PREFIX_TIMESTAMP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SwitchPeriodCommand.MESSAGE_USAGE));
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_TIMESTAMP)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Timestamp pastDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get()).toStartOfDay();

        return new SwitchPeriodCommand(pastDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes are repeated
     * {@code ArgumentMultimap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }
}
