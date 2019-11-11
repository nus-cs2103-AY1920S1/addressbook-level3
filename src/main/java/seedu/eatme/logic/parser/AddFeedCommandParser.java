package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.eatme.logic.commands.AddFeedCommand;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.feed.Feed;

/**
 * Parses input arguments and creates a new AddFeedCommand object
 */
public class AddFeedCommandParser implements Parser<AddFeedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFeedCommand
     * and returns an AddFeedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFeedCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFeedCommand.MESSAGE_USAGE));
        }

        String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
        String address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()).value;

        Feed feed = new Feed(name, address);

        return new AddFeedCommand(feed);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
