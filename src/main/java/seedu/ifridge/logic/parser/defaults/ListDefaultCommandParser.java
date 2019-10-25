package seedu.ifridge.logic.parser.defaults;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_LIST;

import java.util.stream.Stream;

import seedu.ifridge.logic.commands.defaults.ListDefaultCommand;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Prefix;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListDefaultCommand object
 */
public class ListDefaultCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ListDefaultCommand
     * and returns a ListDefaultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListDefaultCommand parse(String args) throws ParseException {
        String d;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LIST);

        if (!arePrefixesPresent(argMultimap, PREFIX_LIST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDefaultCommand.MESSAGE_USAGE));
        } else {
            d = argMultimap.getValue(PREFIX_LIST).get().trim();
        }
        return new ListDefaultCommand(d);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
