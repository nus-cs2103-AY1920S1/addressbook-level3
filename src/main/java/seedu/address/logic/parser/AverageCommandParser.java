package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVGTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AverageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.RecordType;
import seedu.address.model.statistics.AverageType;

/**
 * Parses input arguments and creates a new AverageCommand object
 */
public class AverageCommandParser implements Parser<AverageCommand> {
    private static final String DEFAULT_COUNT_STRING = "5";

    private static final String COUNT_REGEX = "[1-9]";

    /**
     * Parses the given {@code String} of arguments in the context of the AverageCommand
     * and returns a AverageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AverageCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AVGTYPE, PREFIX_RECORDTYPE, PREFIX_COUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_AVGTYPE, PREFIX_RECORDTYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AverageCommand.MESSAGE_USAGE));
        }

        AverageType averageType;

        switch (argMultimap.getValue(PREFIX_AVGTYPE).get().toUpperCase()) {
        case "DAILY":
            //fallthrough
        case "WEEKLY":
            //fallthrough
        case "MONTHLY":
            averageType = AverageType.valueOf(argMultimap.getValue(PREFIX_AVGTYPE).get().toUpperCase());
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                    AverageCommand.MESSAGE_INVALID_AVGTYPE));
        }

        RecordType recordType;

        switch (argMultimap.getValue(PREFIX_RECORDTYPE).get().toUpperCase()) {
        case "DIET":
            //fallthrough
        case "EXERCISE":
            //fallthrough
        case "BLOODSUGAR":
            //fallthrough
        case "HEIGHTANDWEIGHT":
            //fallthrough
        case "MEDICALEXPENSES":
            recordType = RecordType.valueOf(argMultimap.getValue(PREFIX_RECORDTYPE).get().toUpperCase());
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                    AverageCommand.MESSAGE_INVALID_RECORDTYPE));
        }

        String strCount;

        if (arePrefixesPresent(argMultimap, PREFIX_COUNT)) {
            strCount = argMultimap.getValue(PREFIX_COUNT).get();
        } else {
            strCount = DEFAULT_COUNT_STRING;
        }

        if (!strCount.matches(COUNT_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                    AverageCommand.MESSAGE_INVALID_COUNT));
        }

        int count = Integer.parseInt(strCount);

        return new AverageCommand(averageType, recordType, count);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
