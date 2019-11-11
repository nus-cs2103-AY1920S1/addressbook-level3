package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.exceptions.DuplicateTagException;

/**
 * A list of tags that enforces uniqueness between its elements and does not allow nulls.
 * A tag is considered unique by comparing using {@code Tag#equals(Tag)}.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tag> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a tag to the list.
     * The StudyBuddyProItem must not already exist in the list.
     */
    public void add(Tag toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
    }

    //    /**
    //     * Replaces the tag {@code target} in the list with {@code editedTag}.
    //     * {@code target} must exist in the list.
    //     * The tag identity of {@code editedTag} must not be the same as another existing tag in the list.
    //     */
    //    public void setTag(Tag target, Tag editedTag) {
    //        requireAllNonNull(target, editedTag);
    //
    //        int index = internalList.indexOf(target);
    //        if (index == -1) {
    //            throw new TagNotFoundException();
    //        }
    //
    //        if (!target.equals(editedTag) && contains(editedTag)) {
    //            throw new DuplicateTagException();
    //        }
    //
    //        internalList.set(index, editedTag);
    //    }

    //    /**
    //     * Removes the equivalent tag from the list.
    //     * The tag must exist in the list.
    //     */
    //    public void remove(Tag toRemove) {
    //        requireNonNull(toRemove);
    //        if (!internalList.remove(toRemove)) {
    //            throw new TagNotFoundException();
    //        }
    //    }

    /**
     * Replaces the internal list of tags with the internal list of tags within the specified UniqueTagList.
     * @param replacement object that contains the replacement internal list.
     */
    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        requireAllNonNull(tags);
        if (!tagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }

        internalList.setAll(tags);
    }

    /**
     * Adds all tags
     * @param tags list of tags to be added
     */
    public void addAllTags(List<Tag> tags) {
        for (Tag t : tags) {
            this.add(t);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
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
     * Returns true if {@code persons} contains only unique persons.
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
