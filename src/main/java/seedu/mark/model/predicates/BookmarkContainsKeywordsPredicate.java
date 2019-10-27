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
    private IdentifiersContainKeywordsPredicate identifierPredicate;
    private TagContainsKeywordsPredicate tagPredicate;
    private FolderContainsKeywordsPredicate folderPredicate;
    private Predicate<Bookmark> predicate = PREDICATE_SHOW_NO_BOOKMARKS;

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
    public void preparePredicate() {
        predicate = predicate.or(getIdentifierPredicate().orElse(PREDICATE_SHOW_NO_BOOKMARKS));
        predicate = predicate.or(getTagPredicate().orElse(PREDICATE_SHOW_NO_BOOKMARKS));
        predicate = predicate.or(getFolderPredicate().orElse(PREDICATE_SHOW_NO_BOOKMARKS));
    }

    /**
     * Returns true if at least one predicate is present.
     */
    public boolean isAnyPredicatePresent() {
        return CollectionUtil.isAnyNonNull(identifierPredicate, tagPredicate, folderPredicate);
    }

    @Override
    public boolean test(Bookmark bookmark) {
        preparePredicate();
        return predicate.test(bookmark);
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
