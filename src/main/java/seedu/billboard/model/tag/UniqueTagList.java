package seedu.billboard.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UniqueTagList {
    private Map<String, Tag> tagList = new HashMap<>();

    private boolean contains(String tagName) {
        requireNonNull(tagName);
        return tagList.containsKey(tagName);
    }

    /**
     * Retrieves tag which has the tag name given in the argument.
     * The Tag must exist in the list.
     * @param tagName name of the tag.
     * @return Tag of the specific tag name.
     */
    private Tag retrieveTag(String tagName) {
        requireNonNull(tagName);
        return tagList.get(tagName);
    }

    /**
     * Adds a tag with the name given in the argument into the list.
     * Tag must not exist in the list.
     * @param tagName name of the tag.
     */
    private void add(String tagName) {
        requireNonNull(tagName);
        tagList.put(tagName, new Tag(tagName));
    }

    /**
     * Retrieves corresponding tags according to the list of tag names.
     * @param toRetrieve List of tag names.
     * @return Set of all tags retrieved.
     */
    public Set<Tag> retrieveTags(List<String> toRetrieve) {
        Set<Tag> toReturn = new HashSet<>();
        for (int i = 0; i < toRetrieve.size(); i++) {
            String current = toRetrieve.get(i);
            if (!contains(current)) {
                add(current);
            }
            toReturn.add(retrieveTag(current));
        }
        return toReturn;
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
