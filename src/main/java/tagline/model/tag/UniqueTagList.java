package tagline.model.tag;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tagline.model.tag.exceptions.DuplicateTagException;
import tagline.model.tag.exceptions.TagNotFoundException;

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
     * Returns the {@code Tag} which are equals with {@code searchedTag}.
     *
     * @param searchedTag Tag to be compared.
     * @return An optional tag if there is a matching tag.
     */
    public Optional<Tag> findTag(Tag searchedTag) {
        for (Tag tag : internalList) {
            if (searchedTag.equals(tag)) {
                return Optional.of(tag);
            }
        }

        return Optional.empty();
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
     * Removes the equivalent tag from the list.
     * The tag must exist in the list.
     */
    public void removeTag(Tag toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TagNotFoundException();
        }
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
