package seedu.mark.model.autotag;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Manages {@link SelectiveBookmarkTagger}s in Mark.
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
     * @param taggers List of {@link SelectiveBookmarkTagger}s to manage.
     */
    public AutotagController(List<SelectiveBookmarkTagger> taggers) {
        this.taggers = taggers;
    }

    /**
     * Returns a shallow copy of the {@link SelectiveBookmarkTagger} list
     * used by this {@code AutotagController}.
     */
    public List<SelectiveBookmarkTagger> getTaggers() {
        return new ArrayList<>(this.taggers);
    }

    /**
     * Checks whether the given {@link SelectiveBookmarkTagger} exists in this
     * {@code AutotagController}.
     *
     * @param tagger SelectiveBookmarkTagger to be checked.
     * @return true if the {@code tagger} exists, and false otherwise.
     */
    public boolean hasTagger(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);

        return taggers.stream().anyMatch(tagger::equals);
    }

    /**
     * Adds a {@link SelectiveBookmarkTagger} to the existing list of taggers
     * to apply.
     *
     * @param tagger A new SelectiveBookmarkTagger to add.
     */
    public void addTagger(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);
        taggers.add(tagger);
    }

    /**
     * Removes the given {@code tagger} from this {@code AutotagController}.
     *
     * @param tagger {@link SelectiveBookmarkTagger} to be removed.
     * @return {@code false} if the tagger is not found.
     */
    private boolean removeTagger(SelectiveBookmarkTagger tagger) {
        return taggers.remove(tagger);
    }

    /**
     * Removes the {@link SelectiveBookmarkTagger} with the given
     * {@code taggerName} from this {@code AutotagController}.
     *
     * @param taggerName Name of the tag of the {@link SelectiveBookmarkTagger}
     *                   to be removed.
     * @return {@code false} if the tagger is not found.
     */
    public boolean removeTagger(String taggerName) {
        Optional<SelectiveBookmarkTagger> taggerToRemove =
                taggers.stream().filter(tagger -> taggerName.equals(tagger.getTagToApply().tagName))
                        .findFirst();
        // TODO: find a way to use #hasTagger() instead of relying on side effects
        return taggerToRemove.isPresent() ? removeTagger(taggerToRemove.get()) : false;
    }

    /**
     * Removes all taggers from this {@code AutotagController}.
     */
    public void removeAllTaggers() {
        taggers.clear();
    }

    /**
     * Creates a {@link Bookmark} that results from selectively applying all
     * of the current controller's {@code taggers} to the given
     * {@link Bookmark}.
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
     * Applies each of the {@link SelectiveBookmarkTagger}s in {@code taggers}
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
