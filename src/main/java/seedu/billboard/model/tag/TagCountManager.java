package seedu.billboard.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.billboard.model.tag.exceptions.DuplicateTagException;
import seedu.billboard.model.tag.exceptions.TagNotFoundException;

//@@author waifonglee
/**
 * Tracks tags and the number of expenses they are tagged to.
 */
public class TagCountManager {
    private Map<Tag, Integer> count = new HashMap<>();

    /**
     * Checks if tag exists in map.
     * @param tag   tag to be checked.
     * @return      tag's existence.
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
        if (contains(tag)) {
            throw new DuplicateTagException();
        }
        count.put(tag, 0);
    }

    /**
     * Checks and add tags that does not exist in the map.
     * @param tags to be checked.
     */
    public void addNewTags(Set<Tag> tags) {
        requireNonNull(tags);
        for (Tag tag : tags) {
            if (!contains(tag)) {
                add(tag);
            }
        }
    }

    /**
     * Increments count of tag given in the argument.
     * Tag must exist in the map.
     * @param tag whose count to be incremented.
     */
    public void incrementCount(Tag tag) {
        requireNonNull(tag);

        if (!contains(tag)) {
            throw new TagNotFoundException();
        }

        int current = count.get(tag);
        count.replace(tag, current + 1);
    }

    /**
     * Increments count of all the tags in the set given in argument.
     * If tag does not exist, it will be added to the Map.
     * @param tags whose count needs to be incremented by 1.
     */
    public void incrementAllCount(Set<Tag> tags) {
        requireNonNull(tags);
        addNewTags(tags);
        for (Tag tag : tags) {
            incrementCount(tag);
        }
    }

    /**
     * Decrease count of tag given in the argument.
     * Tag must exist in list.
     * @param tag whose count to be decreased.
     */
    public void decreaseCount(Tag tag) {
        requireNonNull(tag);

        if (!contains(tag)) {
            throw new TagNotFoundException();
        }

        int current = count.get(tag);
        count.replace(tag, current - 1);
    }

    /**
     * Decrease count of all tags specified in argument.
     * @param tags whose count to be decreased.
     */
    public void decreaseAllCount(Set<Tag> tags) {
        requireNonNull(tags);
        for (Tag tag : tags) {
            decreaseCount(tag);
        }
    }

    /**
     * Removes all tags whose count = 0 and returns a list of tags removed.
     * @return list of tags removed.
     */
    public List<Tag> removeZeroCount() {
        List<Tag> toReturn = new ArrayList<>();
        Iterator it = count.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Integer num = (Integer) pair.getValue();
            if (num == 0) {
                toReturn.add((Tag) pair.getKey());
                it.remove();
            }
        }
        return toReturn;
    }

    /**
     * Sets the current map to the specified map given in argument.
     * @param count to replace the current map.
     */
    public void setCountMap(Map<Tag, Integer> count) {
        requireNonNull(count);
        this.count = new HashMap<>(count);
    }

    /**
     * Returns an unmodifiable current map.
     * @return current map.
     */
    public Map<Tag, Integer> getCountMap() {
        return Collections.unmodifiableMap(count);
    }

    //@@author
    /**
     * Returns a clone of this object.
     * @return clone of this object.
     */
    public TagCountManager getClone() {
        TagCountManager result = new TagCountManager();
        result.setCountMap(getCountMap());
        return result;
    }

    //@@author waifonglee
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCountManager)) {
            return false;
        }

        TagCountManager otherList = (TagCountManager) other;
        return otherList.getCountMap().equals(getCountMap());
    }
}
