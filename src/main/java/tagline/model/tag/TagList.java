package tagline.model.tag;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import tagline.model.ReadOnlyTagList;
import tagline.model.tag.exceptions.DuplicateTagException;

/**
 * Stores and handles a list of {@code Tag}s.
 */
public class TagList implements Iterable<Tag>, ReadOnlyTagList {
    private List<Tag> tagList;

    {
        tagList = new ArrayList<Tag>();
    }

    public TagList() {
    }

    public TagList(ReadOnlyTagList newData) {
        this();
        requireNonNull(newData);
        setTagList(newData.getTagList());
    }

    /**
     * Replaces the contents of the tag list with {@code replacement}.
     * @throws DuplicateTagException If {@code replacement} contains duplicate tags
     */
    public void setTagList(ReadOnlyTagList replacement) {
        requireNonNull(replacement);
        setTagList(replacement.getTagList());
    }

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * @throws DuplicateTagException If {@code tags} contains duplicate tags
     */
    public void setTagList(List<Tag> tags) {
        requireAllNonNull(tags);
        if (!tagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }

        tagList = new ArrayList<>();
        tagList.addAll(tags);
    }

    /**
     * Returns true if the tag list contains a {@code Tag} with some name.
     *
     * @param tagName The tag name to find
     * @return True if a matching tag was found
     */
    public boolean containsTag(String tagName) {
        requireNonNull(tagName);
        return tagList.stream().anyMatch(t -> t.tagName.equals(tagName));
    }

    /**
     * Returns true if the tag list contains a {@code Tag} with some ID.
     *
     * @param tagId The tag ID to find
     * @return True if a matching tag was found
     */
    public boolean containsTag(int tagId) {
        return tagList.stream().anyMatch(t -> (t.tagId == tagId));
    }

    /**
     * Returns true if the tag list contains a given {@code Tag}.
     *
     * @param toCheck The tag to find
     * @return True if a matching tag was found
     */
    public boolean containsTag(Tag toCheck) {
        requireNonNull(toCheck);
        return tagList.stream().anyMatch(t -> t.equals(toCheck));
    }

    /**
     * Returns a list of {@code Tag}s whose name fully matches a given String.
     *
     * @param tagName The String to match
     * @return A list containing all matching tags
     */
    public List<Tag> findTag(String tagName) {
        requireNonNull(tagName);

        List<Tag> result = new ArrayList<>();
        for (Tag tag : tagList) {
            if (tag.tagName.equals(tagName)) {
                result.add(tag);
            }
        }

        return result;
    }

    /**
     * Returns a list of {@code Tag}s whose ID matches a given value.
     *
     * @param tagId The ID to match
     * @return A list containing all matching tags
     */
    public List<Tag> findTag(int tagId) {
        List<Tag> result = new ArrayList<>();
        for (Tag tag : tagList) {
            if (tag.tagId == tagId) {
                result.add(tag);
                return result; //tags are assumed to be unique
            }
        }

        return result;
    }

    /**
     * Adds a new {@code Tag} to the tag list.
     * @param toAdd The {@code Tag} to add
     */
    public void addTag(Tag toAdd) {
        requireNonNull(toAdd);
        if (containsTag(toAdd)) {
            throw new DuplicateTagException();
        }

        tagList.add(toAdd);
    }

    /**
     * Returns the number of tags in TagLine.
     *
     * @return The number of tags
     */
    public int size() {
        return tagList.size();
    }

    @Override
    public String toString() {
        return size() + " contacts";
        // TODO: refine later
    }

    /**
     * Returns an iterator over the internal list.
     */
    @Override
    public Iterator<Tag> iterator() {
        return tagList.iterator();
    }

    /**
     * Returns a read-only view of the tag list.
     */
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tagList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagList // instanceof handles nulls
                && tagList.equals(((TagList) other).tagList));
    }

    @Override
    public int hashCode() {
        return tagList.hashCode();
    }

    /**
     * Returns true if {@code tags} contains only unique tags.
     */
    private boolean tagsAreUnique(List<Tag> tags) {
        for (int i = 0; i < tags.size() - 1; i++) {
            for (int j = i + 1; j < tags.size(); j++) {
                if (tags.get(i).equals(tags.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
