package seedu.sugarmummy.logic.parser.statistics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_POSSIBLE_COUNT;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_INVALID_COUNT;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_USAGE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_AVGTYPE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import java.util.stream.Stream;

import seedu.sugarmummy.logic.commands.statistics.AverageCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.ParserUtil;
import seedu.sugarmummy.logic.parser.Prefix;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.statistics.predicates.RecordContainsRecordTypePredicate;

//@@author chen-xi-cx

/**
 * Parses input arguments and creates a new AverageCommand object
 */
public class AverageCommandParser implements Parser<AverageCommand> {
    private static final String DEFAULT_COUNT_STRING = "5";

    private static final String COUNT_VALIDATION_REGEX = "^([1-9]|1[012])$";

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AverageCommand and returns a AverageCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AverageCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AVGTYPE, PREFIX_RECORDTYPE, PREFIX_COUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_AVGTYPE, PREFIX_RECORDTYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        AverageType averageType = ParserUtil.parseAverageType(argMultimap.getValue(PREFIX_AVGTYPE).get());
        RecordType recordType = ParserUtil.parseRecordType(argMultimap.getValue(PREFIX_RECORDTYPE).get().toUpperCase());

        String strCount;

        if (arePrefixesPresent(argMultimap, PREFIX_COUNT)) {
            strCount = argMultimap.getValue(PREFIX_COUNT).get();
        } else {
            strCount = DEFAULT_COUNT_STRING;
        }

        if (!strCount.matches(COUNT_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER,
                    MESSAGE_INVALID_COUNT, MESSAGE_POSSIBLE_COUNT));
        }

        int count = Integer.parseInt(strCount);

        return new AverageCommand(new RecordContainsRecordTypePredicate(recordType), averageType, recordType, count);
    }

}
