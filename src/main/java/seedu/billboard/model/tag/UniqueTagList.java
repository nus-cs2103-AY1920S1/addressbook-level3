package seedu.billboard.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A list of tags that enforces uniqueness between its elements and does not allow nulls.
 * A Tag is considered unique by comparing their hashcodes.  As such, adding and updating of
 * Tag uses hashcode and equals() for equality so as to ensure that the Tag being added, updated
 * or removed is unique in terms of identity in the UniqueTagList.
 *
 * Supports a minimal set of Map operations.
 *
 */
public class UniqueTagList {
    private Map<String, Tag> tagList = new HashMap<>();

    /**
     * Checks if tag of specific name exists in list.
      * @param tagName  Name of the tag.
     * @return tag's existence.
     */
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
        return Collections.unmodifiableSet(toReturn);
    }

    /*
    public void remove(String tagName) {
        requireNonNull(tagName);
        tagList.remove(tagName);
    }
     */
}
