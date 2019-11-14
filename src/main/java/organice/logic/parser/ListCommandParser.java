package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import organice.logic.commands.ListCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Type;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Returns the {@code Type} of person in the given {@code ArgumentMultimap}
     * @throws ParseException if the type is not specified correctly in the input arguments,
     * or more than one type parameter is found.
     */
    private static Type parseType(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getAllValues(PREFIX_TYPE).size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND, ListCommand.MESSAGE_USAGE));
        }
        return ParserUtil.parseType(argumentMultimap.getValue(PREFIX_TYPE).get());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE);

        Type type = null;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND, ListCommand.MESSAGE_USAGE));
        } else if (isTypePresent(argMultimap)) {
            type = parseType(argMultimap);
            return new ListCommand(type);
        }
        return new ListCommand(type); // if no type specified return list of all persons
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isTypePresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_TYPE).isPresent();
    }
}
