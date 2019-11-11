//@@author dalsontws

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveBadCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deadline.DueDate;



/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RemoveBadCommandParser implements Parser<RemoveBadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveBadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DUEDATE, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_DUEDATE, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBadCommand.MESSAGE_USAGE));
        }

        DueDate date = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        int i = index.getZeroBased();
        return new RemoveBadCommand(date, i);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
