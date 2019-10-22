package thrift.logic.parser;

import java.time.Month;
import java.util.stream.Stream;

import thrift.commons.core.Messages;
import thrift.logic.commands.ListCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    public static final String MESSAGE_INVALID_MONTH_FORMAT = "Invalid month format! "
            + CliSyntax.PREFIX_MONTH + "MONTH (must be of format MMM)\n";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)
                && !args.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE)); //prefix m/ is not present so there are invalid arguments
        }

        return getCommand(argMultimap);
    }

    private ListCommand getCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)
                && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE)); //prefix m/ exists, but there is invalid preamble before the prefix.
        } else if (arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)) {
            Month month = ParserUtil.parseMonth(argMultimap.getValue(CliSyntax.PREFIX_MONTH).get()); //to be used
            return new ListCommand(); //filter by month tbc, to look like return new ListCommand(month);
        } else {
            return new ListCommand(); //list all transactions, as prefix m/ does not exist.
        }
    }

    /**
     * This methods checks if the required prefixes are present in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the object to check for the existence of prefixes.
     * @param prefixes variable amount of {@code Prefix} to confirm the existence of.
     * @return true if specified prefixes are present in the argumentMultimap.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
