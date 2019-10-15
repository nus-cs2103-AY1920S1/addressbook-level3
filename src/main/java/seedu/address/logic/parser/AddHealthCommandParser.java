package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VALUE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddHealthCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.profile.records.Record;
import seedu.address.profile.records.Timestamp;
import seedu.address.profile.records.Type;
import seedu.address.profile.records.Value;

/**
 * Parses input arguments and creates a new AddHealthCommand object
 */
public class AddHealthCommandParser implements Parser<AddHealthCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddHealthCommand
     * and returns an AddHealthCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddHealthCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_VALUE, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_VALUE, PREFIX_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHealthCommand.MESSAGE_USAGE));
        }

        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        Value value = ParserUtil.parseValue(argMultimap.getValue(PREFIX_VALUE).get());
        Timestamp timestamp = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_DATETIME).get());

        Record record = new Record(type, value, timestamp);

        return new AddHealthCommand(record);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
