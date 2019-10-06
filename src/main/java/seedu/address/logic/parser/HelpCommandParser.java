package seedu.address.logic.parser;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.help.Type;
import seedu.address.model.help.SecondaryCommand;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

/**
 * Parses input arguments and creates a new HelpCommand object
 */

public class HelpCommandParser implements Parser<HelpCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public HelpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMAND, PREFIX_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMMAND, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        SecondaryCommand secondaryCommand = ParserUtil.parseCommand(argMultimap.getValue(PREFIX_COMMAND).get());
        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        return new HelpCommand(secondaryCommand, type);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
