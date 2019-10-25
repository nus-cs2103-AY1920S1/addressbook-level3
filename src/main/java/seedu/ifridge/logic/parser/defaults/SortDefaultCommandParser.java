package seedu.ifridge.logic.parser.defaults;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.stream.Stream;

import seedu.ifridge.logic.commands.defaults.SortDefaultCommand;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Prefix;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortDefaultCommand object
 */
public class SortDefaultCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SortDefaultCommand
     * and returns a SortDefaultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortDefaultCommand parse(String args) throws ParseException {
        String by;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDefaultCommand.MESSAGE_USAGE));
        } else {
            by = argMultimap.getValue(PREFIX_SORT).get().trim();
        }
        return new SortDefaultCommand(by);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
