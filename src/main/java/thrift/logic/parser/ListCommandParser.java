package thrift.logic.parser;

import java.util.Optional;

import thrift.commons.core.Messages;
import thrift.logic.commands.ListCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH, CliSyntax.PREFIX_TAG);
        return getCommand(argMultimap);
    }

    private ListCommand getCommand(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> month = argMultimap.getValue(CliSyntax.PREFIX_MONTH);
        Optional<String> tag = argMultimap.getValue(CliSyntax.PREFIX_TAG);
        if (month.isEmpty() && tag.isEmpty()) {
            return new ListCommand(); //list all transactions
        } else if (!month.isEmpty() && !tag.isEmpty()) {
            return new ListCommand(); //filter by both month and tag, coming in v1.3
        } else if (!month.isEmpty() && tag.isEmpty()) {
            return new ListCommand(); //filter by month, coming in v1.3
        } else if (month.isEmpty() && !tag.isEmpty()) {
            return new ListCommand(); //filter by tag, coming in v1.3
        } else { //bad args input or wrong prefixes used will throw the ParseException
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

}
