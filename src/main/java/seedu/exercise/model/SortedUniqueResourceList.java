package seedu.exercise.model;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.model.exceptions.DuplicateResourceException;
import seedu.exercise.model.exceptions.ResourceNotFoundException;
import seedu.exercise.model.resource.Resource;

/**
 * A list of resources of type {@code T} that enforces uniqueness between its elements and does not allow nulls.
 * The list is also sorted as given by {@code comparator} passed into the constructor.
 * An resource is considered unique by comparing using {@link Resource#isSameResource(Resource)}.
 * As such, adding and updating of resources uses Resource#isSameResource(Resource) for equality so as to ensure that
 * the resource being added or updated is unique in terms of identity in the UniqueResourceList.
 * However, the removal of a resource uses Resource#equals(Object) so as to ensure that the resource with exactly the
 * same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 * </p>
 *
 * @see Resource#isSameResource(Resource)
 */
public class SortedUniqueResourceList<T extends Resource> implements Iterable<T> {

    private final ObservableList<T> internalList;
    private final SortedList<T> internalUnmodifiableSortedList;

    public SortedUniqueResourceList(Comparator<T> comparator) {
        internalList = FXCollections.observableArrayList();
        internalUnmodifiableSortedList = new SortedList<>(internalList, comparator);
    }

    /**
     * Returns true if the {@code UniqueResourceList} instance invoking this method contains {@code toCheck}.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        for (T resource : internalList) {
            if (resource.isSameResource(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds {@code toAdd} into the {@code UniqueResourceList} only if the {@code toAdd} object is not
     * present in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateResourceException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces {@code target} with {@code editedTarget} only if {@code target} is present in the list and
     * {@code editedTarget} is not present in the list.
     */
    public void set(T target, T editedTarget) {
        requireAllNonNull(target, editedTarget);
        int index = internalList.indexOf(target);

        if (index == -1) {
            throw new ResourceNotFoundException();
        }

        if (!target.isSameResource(editedTarget) && contains(editedTarget)) {
            throw new DuplicateResourceException();
        }

        internalList.set(index, editedTarget);
    }

    /**
     * Removes {@code toRemove} from a {@code UniqueResourceList}.
     * The {@code toRemove} object must be present in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Replaces all of the data in the {@code UniqueResourceList} instance with {@code replacement}.
     */
    public void setAll(SortedUniqueResourceList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Works similarly to {@link #setAll(SortedUniqueResourceList)}.
     */
    public void setAll(List<T> replacement) {
        requireNonNull(replacement);
        if (!resourceAreUnique(replacement)) {
            throw new DuplicateResourceException();
        }
        internalList.setAll(replacement);
    }

    public List<T> getAllResourcesIndex(Collection<Index> indexes) {
        List<T> resultingList = new ArrayList<>();
        List<T> unmodifiableList = asUnmodifiableObservableList();
        for (Index index : indexes) {
            resultingList.add(unmodifiableList.get(index.getZeroBased()));
        }
        return resultingList;
    }

    /**
     * Returns an unmodifiable list of the data in a {@code UniqueResourceList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableSortedList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalUnmodifiableSortedList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortedUniqueResourceList)// instanceof handles nulls
            && internalUnmodifiableSortedList.equals(((SortedUniqueResourceList) other).internalUnmodifiableSortedList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if there are no duplicate resources inside the list.
     */
    private boolean resourceAreUnique(List<T> resources) {
        for (int i = 0; i < resources.size() - 1; i++) {
            for (int j = i + 1; j < resources.size(); j++) {
                if (resources.get(i).isSameResource(resources.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
