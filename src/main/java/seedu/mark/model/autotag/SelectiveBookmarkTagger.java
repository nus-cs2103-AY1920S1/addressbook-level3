package seedu.mark.model.autotag;

import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;

/**
 * A type of BookmarkTagger that tags specific Bookmarks only, based on
 * whether they match a given {@code Predicate}.
 */
public class SelectiveBookmarkTagger extends BookmarkTagger {
    private final Predicate<Bookmark> predicate;

    public SelectiveBookmarkTagger(Tag tag, Predicate<Bookmark> predicate) {
        super(tag);
        this.predicate = predicate;
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
        return "Tagger: Applies the tag " + getTagToApply().toString() + " to bookmarks";
        // TODO: Make a better toString()
    }
}
