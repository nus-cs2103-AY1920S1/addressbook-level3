package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;

import java.util.stream.Stream;

import seedu.address.logic.commands.global.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * @param args String of arguments
     * @return ClearCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALL)
                || !argMultimap.getPreamble().isEmpty()) {
            return new ClearCommand(false);
        } else {
            return new ClearCommand(true);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
