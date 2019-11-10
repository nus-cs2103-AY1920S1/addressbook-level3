//@@author dalsontws

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListBadCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deadline.DueDate;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListBadCommandParser implements Parser<ListBadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListBadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DUEDATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DUEDATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListBadCommand.MESSAGE_USAGE));
        }

        DueDate date = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE).get());

        return new ListBadCommand(date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
