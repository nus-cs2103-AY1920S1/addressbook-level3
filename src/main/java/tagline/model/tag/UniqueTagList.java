package tagline.model.tag;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import tagline.model.tag.exceptions.DuplicateTagException;

/**
 * Stores and handles a list of {@code Tag}s.
 */
public class UniqueTagList implements Iterable<Tag> {
    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tag> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Replaces the contents of the tag list with {@code replacement}.
     *
     * @throws DuplicateTagException If {@code replacement} contains duplicate tags
     */
    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of the tag list with {@code tags}.
     *
     * @throws DuplicateTagException If {@code tags} contains duplicate tags
     */
    public void setTags(List<Tag> tags) {
        requireAllNonNull(tags);
        if (!tagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }

        internalList.setAll(tags);
    }

    public OptionalLong findMaxValueOfTagId() {
        return internalList.stream().mapToLong(tag -> tag.getTagId().value).max();
    }

    /**
     * Returns true if the tag list contains a {@code Tag} with some ID.
     *
     * @param tagId The tag ID to find
     * @return True if a matching tag was found
     */
    public boolean containsTag(TagId tagId) {
        return internalList.stream().anyMatch(t -> (t.getTagId().equals(tagId)));
    }

    /**
     * Returns true if the tag list contains a given {@code Tag}.
     *
     * @param toCheck The tag to find
     * @return True if a matching tag was found
     */
    public boolean containsTag(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(t -> t.equals(toCheck));
    }

    /**
     * Returns true if the tag list contains a tag with the same content as the given {@code Tag}.
     *
     * @param tagType The type of tag to find
     * @param content Content of the tag
     * @return True if a matching tag was found
     */
    public boolean containsTag(TagType tagType, String content) {
        requireNonNull(tagType);
        requireNonNull(content);
        return internalList.stream().anyMatch(t -> t.match(tagType, content));
    }

    /**
     * Find id of a tag that matches the {@code tagType} and {@code content}.
     *
     * @param tagType The type of tag to find
     * @param content Content of the tag
     * @return {@code TagId} of a matching tag if found, {@code Optional.empty()} otherwise.
     */
    public Optional<TagId> findTagId(TagType tagType, String content) {
        requireNonNull(tagType);
        requireNonNull(content);
        return internalList.stream()
            .filter(tag -> tag.match(tagType, content))
            .map(tag -> tag.getTagId())
            .findFirst();
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
        /*
        for (Tag tag : tagList) {
            if (tag.getTagName().equals(tagName)) {
                result.add(tag);
            }
        }*/

        return Collections.unmodifiableList(result);
    }

    /**
     * Returns a list of {@code Tag}s whose ID matches a given value.
     *
     * @param tagId The ID to match
     * @return A list containing all matching tags
     */
    public List<Tag> findTag(TagId tagId) {
        List<Tag> result = new ArrayList<>();
        for (Tag tag : internalList) {
            if (tag.getTagId().equals(tagId)) {
                result.add(tag);
                return result; //tags are assumed to be unique
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Adds a new {@code Tag} to the tag list.
     *
     * @param toAdd The {@code Tag} to add
     */
    public void addTag(Tag toAdd) {
        requireNonNull(toAdd);
        if (containsTag(toAdd)) {
            throw new DuplicateTagException();
        }

        internalList.add(toAdd);
    }

    /**
     * Returns the number of tags in TagLine.
     *
     * @return The number of tags
     */
    public int size() {
        return internalList.size();
    }

    @Override
    public String toString() {
        return size() + " tags";
        // TODO: refine later
    }

    /**
     * Returns an iterator over the internal list.
     */
    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns a read-only view of the tag list.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueTagList // instanceof handles nulls
            && internalList.equals(((UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
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
