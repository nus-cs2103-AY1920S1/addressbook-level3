package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.mark.logic.commands.AutotagCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.MatchAllPredicate;
import seedu.mark.model.predicates.MatchNonePredicate;
import seedu.mark.model.predicates.NameContainsKeywordsPredicate;
import seedu.mark.model.predicates.UrlContainsKeywordsPredicate;
import seedu.mark.model.tag.Tag;

/**
 * Parses input arguments and creates a new AutotagCommand object
 */
public class AutotagCommandParser implements Parser<AutotagCommand> {

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

        BookmarkPredicateBuilder bookmarkPredicateBuilder = new BookmarkPredicateBuilder();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            bookmarkPredicateBuilder.addCondition(
                    new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_URL).isPresent()) {
            bookmarkPredicateBuilder.addCondition(
                    new UrlContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_URL)));
        }

        if (!bookmarkPredicateBuilder.hasConditions()) {
            throw new ParseException(AutotagCommand.MESSAGE_NO_CONDITION_SPECIFIED);
        }

        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(tagToApply, bookmarkPredicateBuilder.build());

        return new AutotagCommand(tagger);
    }

    /**
     * Stores details of conditions that the final {@code Predicate<Bookmark>}
     * should either try to match or try to not match.
     */
    private static final class BookmarkPredicateBuilder {
        private List<Predicate<Bookmark>> conditionsToMatch;
        private List<Predicate<Bookmark>> conditionsToNotMatch;

        public BookmarkPredicateBuilder() {
            conditionsToMatch = new ArrayList<>();
            conditionsToNotMatch = new ArrayList<>();
        }

        public BookmarkPredicateBuilder addCondition(Predicate<Bookmark> predicate) {
            conditionsToMatch.add(predicate);
            return this;
        }

        // for future use
        public BookmarkPredicateBuilder addNotCondition(Predicate<Bookmark> predicate) {
            conditionsToNotMatch.add(predicate);
            return this;
        }

        public boolean hasConditions() {
            return !(conditionsToMatch.isEmpty() && conditionsToNotMatch.isEmpty());
        }

        public Predicate<Bookmark> build() {
            List<Predicate<Bookmark>> allPredicates = new ArrayList<>(conditionsToMatch);
            allPredicates.add(new MatchNonePredicate(conditionsToNotMatch));
            return new MatchAllPredicate(allPredicates);
        }
    }
}
