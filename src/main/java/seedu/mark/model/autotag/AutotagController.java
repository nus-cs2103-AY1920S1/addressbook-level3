package seedu.mark.model.autotag;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Manages {@link SelectiveBookmarkTagger}s in Mark.
 */
public class AutotagController {
    private final ObservableList<SelectiveBookmarkTagger> taggers;

    /**
     * Creates a new {@code AutotagController} with an empty list of taggers.
     */
    public AutotagController() {
        this.taggers = FXCollections.observableList(new ArrayList<>());
    }

    /**
     * Creates a new {@code AutotagController} with the given list of taggers.
     *
     * @param taggers List of {@link SelectiveBookmarkTagger}s to manage.
     */
    public AutotagController(ObservableList<SelectiveBookmarkTagger> taggers) {
        this.taggers = taggers;
    }

    /**
     * Returns the observable list of {@link SelectiveBookmarkTagger}s
     * used by this {@code AutotagController}.
     */
    public ObservableList<SelectiveBookmarkTagger> getTaggers() {
        return this.taggers;
    }

    /**
     * Checks whether a {@link SelectiveBookmarkTagger} with the same name as the
     * given {@code tagger} exists in this {@code AutotagController}.
     *
     * @param tagger SelectiveBookmarkTagger to be checked.
     * @return true if a {@code tagger} with the same name exists, and false otherwise.
     */
    public boolean hasTagger(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);

        return taggers.stream().map(BookmarkTagger::getTagToApply)
                .anyMatch(tagger.getTagToApply()::equals);
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
     */
    private void removeTagger(SelectiveBookmarkTagger tagger) {
        taggers.remove(tagger);
    }

    /**
     * Removes the {@link SelectiveBookmarkTagger} with the given
     * {@code taggerName} from this {@code AutotagController}.
     *
     * @param taggerName Name of the tag of the {@link SelectiveBookmarkTagger}
     *                   to be removed.
     * @return An {@code Optional} containing the {@link SelectiveBookmarkTagger}
     *         that was removed if the tagger exists, and an empty {@code Optional}
     *         otherwise.
     */
    public Optional<SelectiveBookmarkTagger> removeTagger(String taggerName) {
        requireNonNull(taggerName);
        Optional<SelectiveBookmarkTagger> taggerToRemove = taggers.stream()
                .filter(tagger -> taggerName.equals(tagger.getTagToApply().tagName))
                .findFirst();
        if (taggerToRemove.isEmpty()) {
            return Optional.empty();
        }
        removeTagger(taggerToRemove.get());
        return taggerToRemove;
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
