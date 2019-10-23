package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteFeedCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFeedCommand object
 */
public class DeleteFeedCommandParser implements Parser<DeleteFeedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFeedCommand
     * and returns an DeleteFeedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFeedCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFeedCommand.MESSAGE_USAGE));
        }

        String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;

        return new DeleteFeedCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
