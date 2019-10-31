package tagline.model.tag;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * The API of the TagModel component.
 */
public interface TagModel {
    /**
     * Replaces tag book data with the data in {@code tagBook}.
     */
    void setTagBook(ReadOnlyTagBook tagList);

    /**
     * Returns a read-only view of the tag book.
     */
    ReadOnlyTagBook getTagBook();

    /**
     * Returns true if {@code tag} exists in the tag list.
     */
    boolean hasTag(Tag tag);

    /**
     * Adds the given tag.
     * {@code tag} must not already exist in the tag list.
     */

    void addTag(Tag tag);

    /**
     * Deletes the given tag.
     * {@code tag} must already exist in the tag list.
     */
    void deleteTag(Tag tag);

    /**
     * Returns an unmodifiable view of the filtered tag list.
     */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /**
     * Returns an unmodifiable copy of the filtered tag list with a set predicate.
     */
    ObservableList<Tag> getFilteredTagListWithPredicate(Predicate<Tag> predicate);

    /**
     * Finds tag inside of TagModel.
     */
    Optional<Tag> findTag(Tag tag);
}
