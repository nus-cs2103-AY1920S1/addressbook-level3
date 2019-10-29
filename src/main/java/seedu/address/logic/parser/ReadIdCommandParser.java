package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.ReadIdCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReadIdCommand object
 */
public class ReadIdCommandParser implements Parser<ReadIdCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReadIdCommand
     * and returns a ReadIdCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReadIdCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_CUSTOMER, PREFIX_DRIVER);

        if (ParserUtil.getNoOfPrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_CUSTOMER, PREFIX_DRIVER) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadIdCommand.MESSAGE_USAGE));
        }

        Prefix foundPrefix = getPrefixPresent(argMultimap, PREFIX_TASK, PREFIX_CUSTOMER, PREFIX_DRIVER);
        int id = ParserUtil.parseId(argMultimap.getValue(foundPrefix).get());

        String className = foundPrefix.getPrefixClass();

        return new ReadIdCommand(className, id);
    }

    private static Prefix getPrefixPresent(ArgumentMultimap argumentMultimap,
                                           Prefix... prefixes) throws ParseException {
        Optional<Prefix> prefixFound = Stream
                .of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .findFirst();
        if (prefixFound.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadIdCommand.MESSAGE_USAGE));
        }

        return prefixFound.get();
    }
}
