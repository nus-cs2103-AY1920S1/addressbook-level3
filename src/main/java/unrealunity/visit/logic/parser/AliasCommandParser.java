package unrealunity.visit.logic.parser;

import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_ALIAS_NAME;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_ALIAS_VALUE;

import java.util.stream.Stream;

import unrealunity.visit.logic.commands.AliasCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS_NAME, PREFIX_ALIAS_VALUE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS_NAME, PREFIX_ALIAS_VALUE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_ALIAS_NAME).get();
        String value = argMultimap.getValue(PREFIX_ALIAS_VALUE).get();

        return new AliasCommand(name, value);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
