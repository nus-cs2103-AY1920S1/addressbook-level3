package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.mark.logic.commands.FindCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.predicates.BookmarkContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_FOLDER);
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String preamble = argMultimap.getPreamble();
        List<String> identifierKeywords = new LinkedList<>(Arrays.asList(preamble.split("\\s+")));
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        List<String> folderKeywords = argMultimap.getAllValues(PREFIX_FOLDER);
        // Remove keyword empty string
        identifierKeywords.removeIf(String::isEmpty);
        tagKeywords.removeIf(String::isEmpty);
        folderKeywords.removeIf(String::isEmpty);

        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(identifierKeywords,
                tagKeywords, folderKeywords);

        if (!predicate.isAnyKeywordPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }
}
