package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS_ALIAS_INPUT, PREFIX_ALIAS_ALIAS_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        Optional<String> aliasName = argMultimap.getValue(PREFIX_ALIAS_ALIAS_NAME);
        Optional<String> aliasInputMapping = argMultimap.getValue(PREFIX_ALIAS_ALIAS_INPUT);

        if (aliasName.isEmpty() || aliasInputMapping.isEmpty() || aliasName.get().isEmpty()
                || aliasInputMapping.get().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        Alias alias = ParserUtil.parseAlias(aliasName.get(), aliasInputMapping.get());

        return new AliasCommand(alias);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
