package seedu.billboard.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagCountManager {
    private Map<Tag, Integer> count = new HashMap<>();

    /**
     * Checks if tag exists in map.
     * @param tag tag to be checked.
     * @return tag's existence.
     */
    public boolean contains(Tag tag) {
        requireNonNull(tag);
        return count.containsKey(tag);
    }

    /**
     * Adds a tag given in the argument into the map.
     * Tag must not exist in the map.
     * @param tag to be added.
     */
    public void add(Tag tag) {
        requireNonNull(tag);
        count.put(tag, 0);
    }

    /**
     * Checks and add tags that does not exist in the list.
     * @param tags to be checked.
     */
    public void addNewTags(List<Tag> tags) {
        for (Tag tag : tags) {
            if (!contains(tag)) {
                add(tag);
            }
        }
    }

    /**
     * Increments count of tag given in the argument.
     * @param tag whose count to be incremented.
     */
    public void incrementCount(Tag tag) {
        int current = count.get(tag);
        count.replace(tag, current + 1);
    }

    /**
     * Increments count of all the tags in the list given in argument.
     * @param tags whose count to be incremented.
     */
    public void incrementAllCount(List<Tag> tags) {
        addNewTags(tags);
        for (Tag tag : tags) {
            incrementCount(tag);
        }
    }
}
