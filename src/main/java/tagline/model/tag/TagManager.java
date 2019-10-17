package tagline.model.tag;

import java.util.List;

/**
 * Represents the in-memory model of all tag data.
 */
public class TagManager implements TagModel {
    private final TagList tagList;

    /**
     * Initializes a TagManager with the given tagList.
     */
    public TagManager(ReadOnlyTagList tagList) {
        this.tagList = new TagList(tagList);
    }

    public void setTagList(ReadOnlyTagList tagList) {
        this.tagList.setTagList(tagList);
    }

    public ReadOnlyTagList getTagList() {
        return this.tagList;
    }

    public boolean containsTag(Tag tag) {
        return tagList.containsTag(tag);
    }

    public void addTag(Tag tag) {
        tagList.addTag(tag);
    }

    public List<Tag> findTag(TagId tagId) {
        return tagList.findTag(tagId);
    }
}
