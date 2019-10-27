package seedu.mark.model.predicates;

import static seedu.mark.model.Model.PREDICATE_SHOW_NO_BOOKMARKS;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.mark.commons.util.CollectionUtil;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Tests that part of a {@code Bookmark}'s data fields (except remark) matches any of the keywords given.
 */
public class BookmarkContainsKeywordsPredicate implements Predicate<Bookmark> {
    private static final Predicate<Bookmark> DEFAULT_PREDICATE = PREDICATE_SHOW_NO_BOOKMARKS;

    private IdentifiersContainKeywordsPredicate identifierPredicate;
    private TagContainsKeywordsPredicate tagPredicate;
    private FolderContainsKeywordsPredicate folderPredicate;

    public BookmarkContainsKeywordsPredicate() {}

    public BookmarkContainsKeywordsPredicate(List<String> identifierKeywords, List<String> tagKeywords,
                                             List<String> folderKeywords) {
        setIdentifierPredicate(identifierKeywords);
        setTagPredicate(tagKeywords);
        setFolderPredicate(folderKeywords);
    }

    public Optional<Predicate<Bookmark>> getIdentifierPredicate() {
        return Optional.ofNullable(identifierPredicate);
    }

    public Optional<Predicate<Bookmark>> getTagPredicate() {
        return Optional.ofNullable(tagPredicate);
    }

    public Optional<Predicate<Bookmark>> getFolderPredicate() {
        return Optional.ofNullable(folderPredicate);
    }

    public void setIdentifierPredicate(List<String> keywords) {
        if (keywords.isEmpty()) {
            identifierPredicate = null;
        } else {
            identifierPredicate = new IdentifiersContainKeywordsPredicate(keywords);
        }
    }

    public void setTagPredicate(List<String> keywords) {
        if (keywords.isEmpty()) {
            tagPredicate = null;
        } else {
            tagPredicate = new TagContainsKeywordsPredicate(keywords);
        }
    }

    public void setFolderPredicate(List<String> keywords) {
        if (keywords.isEmpty()) {
            folderPredicate = null;
        } else {
            folderPredicate = new FolderContainsKeywordsPredicate(keywords);
        }
    }

    /**
     * Prepares the predicate for test
     */
    public Predicate<Bookmark> preparePredicate() {
        return DEFAULT_PREDICATE.or(getIdentifierPredicate().orElse(DEFAULT_PREDICATE))
                .or(getTagPredicate().orElse(DEFAULT_PREDICATE))
                .or(getFolderPredicate().orElse(DEFAULT_PREDICATE));
    }

    /**
     * Returns true if at least one predicate is present.
     */
    public boolean isAnyPredicatePresent() {
        return CollectionUtil.isAnyNonNull(identifierPredicate, tagPredicate, folderPredicate);
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return preparePredicate().test(bookmark);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkContainsKeywordsPredicate // instanceof handles nulls
                && getIdentifierPredicate().equals(((BookmarkContainsKeywordsPredicate) other).getIdentifierPredicate())
                && getTagPredicate().equals(((BookmarkContainsKeywordsPredicate) other).getTagPredicate())
                && getFolderPredicate().equals(((BookmarkContainsKeywordsPredicate) other).getFolderPredicate()));
    }
}
