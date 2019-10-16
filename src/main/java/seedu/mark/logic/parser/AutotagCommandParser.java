package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.function.Predicate;

import seedu.mark.logic.commands.AutotagCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.NameContainsKeywordsPredicate;
import seedu.mark.model.predicates.UrlContainsKeywordsPredicate;
import seedu.mark.model.tag.Tag;

/**
 * Parses input arguments and creates a new AutotagCommand object
 */
public class AutotagCommandParser implements Parser<AutotagCommand> {

    public static final Predicate<Bookmark> DEFAULT_PREDICATE = bookmark -> true;

    /**
     * Parses the given {@code String} of arguments in the context of the AutotagCommand
     * and returns an AutotagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutotagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_URL);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutotagCommand.MESSAGE_USAGE));
        }

        Tag tagToApply = ParserUtil.parseTag(argMultimap.getPreamble());

        boolean hasConditions = false;
        Predicate<Bookmark> predicate = DEFAULT_PREDICATE;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            hasConditions = true;
            predicate = predicate.and(new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_URL).isPresent()) {
            hasConditions = true;
            predicate = predicate.and(new UrlContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_URL)));
        }

        // for not containing, just do
        // new UrlContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_URL)).negate() to get that predicate

        if (!hasConditions) {
            throw new ParseException(AutotagCommand.MESSAGE_NO_CONDITION_SPECIFIED);
        }

        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(tagToApply, predicate);

        return new AutotagCommand(tagger);
    }

}
