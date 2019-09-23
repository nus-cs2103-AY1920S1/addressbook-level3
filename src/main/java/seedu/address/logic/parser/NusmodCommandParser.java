package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.NusmodCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NusmodCommand object.
 */
public class NusmodCommandParser implements Parser<NusmodCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public NusmodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NusmodCommand.MESSAGE_USAGE));
        }

        String input = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULECODE).get());

        String[] tokens = input.split(" ");
        String moduleCode = tokens[0];

        if (tokens.length == 2) {
            return new NusmodCommand(moduleCode, tokens[1]);
        } else if (tokens.length == 3) {
            return new NusmodCommand(moduleCode, tokens[1], tokens[2]);
        } else {
            return new NusmodCommand(moduleCode);
        }

    }
}
