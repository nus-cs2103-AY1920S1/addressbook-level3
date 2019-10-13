package seedu.billboard.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

public class UniqueTagList {
    private Map<String, Tag> tagList = new HashMap<>();

    public boolean contains(String tagName) {
        requireNonNull(tagName);
        return tagList.containsKey(tagName);
    }

    /**
     * Retrieves tag which has the tag name given in the argument.
     * The Tag must exist in the list.
     * @param tagName name of the tag.
     * @return Tag of the specific tag name.
     */
    public Tag retrieveTag(String tagName) {
        requireNonNull(tagName);
        return tagList.get(tagName);
    }

    /**
     * Adds a tag with the name given in the argument into the list.
     * Tag must not exist in the list.
     * @param tagName name of the tag.
     */
    public void add(String tagName) {
        requireNonNull(tagName);
        tagList.put(tagName, new Tag(tagName));
    }

    /**
     * Removes the mapping corresponding to the tag name given in the argument.
     * @param tagName name of the tag.
     */
    public void remove(String tagName) {
        requireNonNull(tagName);
        tagList.remove(tagName);
    }
}
