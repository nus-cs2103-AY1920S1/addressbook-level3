package tagline.model;

import java.util.List;

import tagline.model.tag.Tag;

/**
 * Unmodifiable view of a tag list.
 */
public interface ReadOnlyTagList {

    /**
     * Returns an unmodifiable view of the tag list.
     * This list will not contain any duplicate tags.
     */
    List<Tag> getTagList();

}
