package seedu.mark.model.autotag;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;

/**
 * Defines a class that applies {@code Tag}s to {@code Bookmark}s.
 */
public class BookmarkTagger {
    private final Tag tagToApply;

    public BookmarkTagger(Tag tag) {
        requireNonNull(tag);
        this.tagToApply = tag;
    }

    public Tag getTagToApply() {
        return this.tagToApply;
    }

    /**
     * Creates a new Bookmark that is tagged with {@code tagToApply}.
     *
     * @param bookmark Bookmark to be tagged.
     * @return A new Bookmark containing {@code tagToApply}.
     */
    public Bookmark applyTag(Bookmark bookmark) {
        requireNonNull(bookmark);

        Set<Tag> updatedTags = new HashSet<>(bookmark.getTags());
        updatedTags.add(new Tag(tagToApply)); // does not allow duplicate tags

        return new Bookmark(bookmark.getName(), bookmark.getUrl(),
                bookmark.getRemark(), bookmark.getFolder(), updatedTags, bookmark.getCachedCopies());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BookmarkTagger // instanceof handles nulls
                && getTagToApply().equals(((BookmarkTagger) other).getTagToApply())); // state check
    }
}
