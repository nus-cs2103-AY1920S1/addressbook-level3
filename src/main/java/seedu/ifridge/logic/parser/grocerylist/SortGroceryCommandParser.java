package seedu.ifridge.logic.parser.grocerylist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.stream.Stream;

import seedu.ifridge.logic.commands.grocerylist.SortGroceryCommand;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Prefix;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortGroceryCommand object
 */
public class SortGroceryCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SortGroceryCommand
     * and returns a SortGroceryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortGroceryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGroceryCommand.MESSAGE_USAGE));
        }

        String d = argMultimap.getValue(PREFIX_SORT).get().trim();
        return new SortGroceryCommand(d);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
