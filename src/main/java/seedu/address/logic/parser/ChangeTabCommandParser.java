package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAB_CHANGE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ChangeTabCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.WindowView;



/**
 * Parses input arguments and creates a new ChangeTabCommand object.
 */
public class ChangeTabCommandParser implements Parser<ChangeTabCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an ChangeTabCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeTabCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAB_CHANGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAB_CHANGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTabCommand.MESSAGE_USAGE));
        }

        WindowView view = ParserUtil.parseWindowView(argMultimap.getValue(PREFIX_TAB_CHANGE).get());

        return new ChangeTabCommand(view);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
