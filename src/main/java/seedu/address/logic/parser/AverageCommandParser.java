package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NUMBER;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARAMETER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVGTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AverageCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AverageCommand object
 */
public class AverageCommandParser implements Parser<AverageCommand> {
    private static final String DEFAULT_COUNT_STRING = "5";

    public enum RecordType {
        DIET, EXERCISE, BLOODSUGAR, HEIGHTANDWEIGHT, MEDICALEXPENSES
    }

    public enum AvgType {
        DAILY, WEEKLY, MONTHLY
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AverageCommand
     * and returns a AverageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AverageCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AVGTYPE, PREFIX_RECORDTYPE, PREFIX_COUNT);

        AvgType avgType;
        switch (argMultimap.getValue(PREFIX_AVGTYPE).get().toLowerCase()) {
        case "daily":
            avgType = AvgType.DAILY;
            break;
        case "weekly":
            avgType = AvgType.WEEKLY;
            break;
        case "monthly":
            avgType = AvgType.MONTHLY;
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AverageCommand.MESSAGE_USAGE));
        }

        RecordType recordType;
        switch (argMultimap.getValue(PREFIX_RECORDTYPE).get().toLowerCase()) {
        case "diet":
            recordType = RecordType.DIET;
            break;
        case "exercise":
            recordType = RecordType.EXERCISE;
            break;
        case "bloodsugar":
            recordType = RecordType.BLOODSUGAR;
            break;
        case "heightandweight":
            recordType = RecordType.HEIGHTANDWEIGHT;
            break;
        case "medicalexpenses":
            recordType = RecordType.MEDICALEXPENSES;
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AverageCommand.MESSAGE_USAGE));
        }

        String strCount;
        if (arePrefixesPresent(argMultimap, PREFIX_COUNT)) {
            strCount = argMultimap.getValue(PREFIX_COUNT).get();
        } else {
            strCount = DEFAULT_COUNT_STRING;
        }

        int count;

        try {
            count = Integer.parseInt(strCount);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_PARAMETER, AverageCommand.MESSAGE_USAGE,
                    AverageCommand.MESSAGE_INVALID_COUNT));
        }
        return new AverageCommand(avgType, recordType, count);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
