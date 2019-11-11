package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFF;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.util.stream.Stream;

import seedu.address.logic.commands.SuggestionSwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SuggestionSwitchCommand object
 */
public class SuggestionSwitchCommandParser implements Parser<SuggestionSwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestionSwitchCommand
     * and returns an SuggestionSwitchCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not conform the expected
     * format
     */
    public SuggestionSwitchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ON, PREFIX_OFF);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_ON, PREFIX_OFF)
                || arePrefixesPresent(argMultimap, PREFIX_ON, PREFIX_OFF)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SuggestionSwitchCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_ON).isPresent()) {
            return new SuggestionSwitchCommand(true);
        } else {
            return new SuggestionSwitchCommand(false);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
