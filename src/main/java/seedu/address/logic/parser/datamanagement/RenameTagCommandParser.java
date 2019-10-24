package seedu.address.logic.parser.datamanagement;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.TAG_PATTERN;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RenameTagCommand object
 */
public class RenameTagCommandParser implements Parser<RenameTagCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RenameTagCommand
     * and returns an RenameTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, TAG_PATTERN);

        if (!arePrefixesPresent(argMultimap, TAG_PATTERN)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RenameTagCommand.MESSAGE_USAGE));
        }
        List<String> tagNames = ParserUtil.parseTags(argMultimap.getAllValues(TAG_PATTERN));
        String oldTag = tagNames.get(0);
        String newTag = tagNames.get(1);
        return new RenameTagCommand(oldTag, newTag);
    }

}
