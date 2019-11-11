package seedu.mark.model.autotag;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;

/**
 * A type of BookmarkTagger that tags specific Bookmarks only, based on
 * whether they match a given {@code BookmarkPredicate}.
 */
public class SelectiveBookmarkTagger extends BookmarkTagger {

    private final BookmarkPredicate predicate;

    public SelectiveBookmarkTagger(Tag tag, BookmarkPredicate predicate) {
        super(tag);
        this.predicate = predicate;
    }

    /**
     * Returns the {@code BookmarkPredicate} used in this {@code SelectiveBookmarkTagger}.
     */
    public BookmarkPredicate getPredicate() {
        return predicate;
    }

    /**
     * Checks whether this {@code SelectiveBookmarkTagger} has an empty predicate.
     *
     * @return true if this tagger's {@code BookmarkPredicate} is empty, and
     *         false otherwise.
     */
    public boolean hasEmptyPredicate() {
        return predicate.isEmpty();
    }

    /**
     * Tests the given {@code Bookmark} and tags it only if it matches this
     * {@code SelectiveBookmarkTagger}'s {@code predicate}.
     *
     * @param bookmark A bookmark to test and possibly tag.
     * @return A new tagged Bookmark if the bookmark matches the predicate, and
     *         the original Bookmark otherwise.
     */
    public Bookmark applyTagSelectively(Bookmark bookmark) {
        return predicate.test(bookmark) ? super.applyTag(bookmark) : bookmark;
    }

    @Override
    public String toString() {
        return "Tagger: Applies the tag " + getTagToApply().toString() + " to bookmarks "
                + "that match the predicate " + predicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectiveBookmarkTagger // instanceof handles nulls
                && super.equals(other))
                && getPredicate().equals(((SelectiveBookmarkTagger) other).getPredicate()); // state check
    }
}
