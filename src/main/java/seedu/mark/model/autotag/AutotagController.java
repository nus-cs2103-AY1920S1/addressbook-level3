package seedu.mark.model.autotag;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Manages {@code SelectiveBookmarkTagger}s in Mark.
 */
public class AutotagController {
    private final List<SelectiveBookmarkTagger> taggers;

    /**
     * Creates a new {@code AutotagController} with an empty list of taggers.
     */
    public AutotagController() {
        this.taggers = new ArrayList<>();
    }

    /**
     * Creates a new {@code AutotagController} with the given list of taggers.
     *
     * @param taggers List of {@code SelectiveBookmarkTagger}s to manage.
     */
    public AutotagController(List<SelectiveBookmarkTagger> taggers) {
        this.taggers = taggers;
    }

    private List<SelectiveBookmarkTagger> getTaggers() {
        return this.taggers;
    }

    /**
     * Adds a {@code SelectiveBookmarkTagger} to the existing list of taggers
     * to apply.
     *
     * @param tagger A new SelectiveBookmarkTagger to add.
     */
    public void addTagger(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);
        taggers.add(tagger);
    }

    /**
     * Creates a {@code Bookmark} that results from selectively applying all
     * of the current controller's {@code taggers} to the given
     * {@code Bookmark}.
     *
     * @param bookmark Bookmark to be tagged
     * @return A tagged Bookmark
     */
    public Bookmark applyTaggers(Bookmark bookmark) {
        requireNonNull(bookmark);

        Bookmark taggedBookmark = bookmark;
        for (SelectiveBookmarkTagger tagger : taggers) {
            taggedBookmark = tagger.applyTagSelectively(taggedBookmark);
        }
        return taggedBookmark;
    }

    /**
     * Applies each of the {@code SelectiveBookmarkTagger}s in {@code taggers}
     * to all of the bookmarks in the given list.
     *
     * @param bookmarks List of Bookmarks that each tagger should be applied to.
     * @return List of Bookmarks containing the tagged bookmarks.
     */
    public List<Bookmark> applyTaggersToList(List<Bookmark> bookmarks) {
        return bookmarks.stream().map(this::applyTaggers).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AutotagController // instanceof handles nulls
                && getTaggers().equals(((AutotagController) other).getTaggers())); // state check
    }
}
