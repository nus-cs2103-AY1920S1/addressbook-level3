package seedu.mark.model.predicates;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.model.Model.PREDICATE_SHOW_NO_BOOKMARKS;

import java.util.List;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Tests that part of a {@code Bookmark}'s data fields (except remark) matches any of the keywords given.
 */
public class BookmarkContainsKeywordsPredicate implements Predicate<Bookmark> {
    private static final Predicate<Bookmark> DEFAULT_PREDICATE = PREDICATE_SHOW_NO_BOOKMARKS;

    private List<String> identifierKeywords;
    private List<String> tagKeywords;
    private List<String> folderKeywords;

    public BookmarkContainsKeywordsPredicate(List<String> identifierKeywords, List<String> tagKeywords,
                                             List<String> folderKeywords) {
        requireAllNonNull(identifierKeywords, tagKeywords, folderKeywords);
        this.identifierKeywords = identifierKeywords;
        this.tagKeywords = tagKeywords;
        this.folderKeywords = folderKeywords;
    }

    /**
     * Prepares the predicate for test
     */
    public Predicate<Bookmark> getPredicate() {
        return DEFAULT_PREDICATE.or(new IdentifiersContainKeywordsPredicate(identifierKeywords))
                .or(new TagContainsKeywordsPredicate(tagKeywords))
                .or(new FolderContainsKeywordsPredicate(folderKeywords));
    }

    /**
     * Returns true if at least one keyword is present.
     */
    public boolean isAnyKeywordPresent() {
        return !(identifierKeywords.isEmpty() && tagKeywords.isEmpty() && folderKeywords.isEmpty());
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return getPredicate().test(bookmark);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkContainsKeywordsPredicate // instanceof handles nulls
                && identifierKeywords.equals(((BookmarkContainsKeywordsPredicate) other).identifierKeywords)
                && tagKeywords.equals(((BookmarkContainsKeywordsPredicate) other).tagKeywords)
                && folderKeywords.equals(((BookmarkContainsKeywordsPredicate) other).folderKeywords));
    }
}
