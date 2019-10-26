package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.model.Model.PREDICATE_SHOW_NO_BOOKMARKS;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.mark.logic.commands.FindCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.FolderContainsKeywordsPredicate;
import seedu.mark.model.predicates.IdentifiersContainKeywordsPredicate;
import seedu.mark.model.predicates.TagContainsKeywordsPredicate;

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

        Predicate<Bookmark> predicate = PREDICATE_SHOW_NO_BOOKMARKS;
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        List<String> folderKeywords = argMultimap.getAllValues(PREFIX_FOLDER);
        String preamble = argMultimap.getPreamble();
        tagKeywords.removeIf(String::isEmpty);
        folderKeywords.removeIf(String::isEmpty);
        if (tagKeywords.isEmpty() && folderKeywords.isEmpty() && preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (!tagKeywords.isEmpty()) {
            predicate = predicate.or(new TagContainsKeywordsPredicate(tagKeywords));
        }
        if (!folderKeywords.isEmpty()) {
            predicate = predicate.or(new FolderContainsKeywordsPredicate(folderKeywords));
        }
        if (!preamble.isEmpty()) {
            String[] nameKeywords = preamble.split("\\s+");
            predicate = predicate.or(new IdentifiersContainKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        return new FindCommand(predicate);
    }

}
