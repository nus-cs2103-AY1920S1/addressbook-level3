package seedu.address.itinerary.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.itinerary.commands.SortCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());

        return new SortCommand(type);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
