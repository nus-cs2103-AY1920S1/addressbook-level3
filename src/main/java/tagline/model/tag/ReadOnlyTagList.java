package tagline.model.tag;

import java.util.List;

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
