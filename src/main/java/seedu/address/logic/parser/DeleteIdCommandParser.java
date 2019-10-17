package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteIdCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteIdCommand object
 */
public class DeleteIdCommandParser implements Parser<DeleteIdCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIdCommand
     * and returns a DeleteIdCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteIdCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_CUSTOMER, PREFIX_DRIVER);

        //check if only 1 of the 3 prefix is input
        if (getNoOfPrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_CUSTOMER, PREFIX_DRIVER) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteIdCommand.MESSAGE_USAGE));
        }

        Prefix foundPrefix = getPrefixPresent(argMultimap, PREFIX_TASK, PREFIX_CUSTOMER, PREFIX_DRIVER);
        int id = ParserUtil.parseId(argMultimap.getValue(foundPrefix).get());

        String className = foundPrefix.getPrefixClass();

        return new DeleteIdCommand(className, id);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areOneOfPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static int getNoOfPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent()).count();
    }

    private static Prefix getPrefixPresent(ArgumentMultimap argumentMultimap,
                                            Prefix... prefixes) throws ParseException {
        Optional<Prefix> prefixFound = Stream
                                            .of(prefixes)
                                            .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                                            .findFirst();
        if (prefixFound.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteIdCommand.MESSAGE_USAGE));
        }

        return prefixFound.get();
    }
}
