package tagline.model.tag;

import java.util.Optional;

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
     * Finds tag inside of TagModel.
     */
    Optional<Tag> findTag(Tag tag);
}
