package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of {@code Identifiable} objects that enforces uniqueness between its elements and does not allow nulls.
 * An {@code Identifiable} objects is considered unique by comparing using {@code Identifiable#isSameAs(Identifiable)}.
 * As such, adding and updating of
 * objects uses Identifiable#isSameAs(Identifiable) for equality so as to ensure that the
 * object being added or updated is
 * unique in terms of identity in the UniqueList.
 * However, the removal of an object uses Identifiable#equals(Object) so
 * as to ensure that the object with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @param <T> A class that implements {@code Identifiable}.
 */
public class UniqueList<T extends Identifiable<T>> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent {@code Identifiable} object as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAs);
    }

    /**
     * Adds an {code Identifiable} object to the list.
     * The object must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIdentityException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the {@code Identifiable} object {@code target} in the list with {@code editedObject}.
     * {@code target} must exist in the list.
     * The identity of {@code editedObject} must not be the same as another existing object in the list.
     */
    public void set(T target, T editedObject) {
        requireAllNonNull(target, editedObject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new IdentityNotFoundException();
        }

        if (!target.isSameAs(editedObject) && contains(editedObject)) {
            throw new DuplicateIdentityException();
        }

        internalList.set(index, editedObject);
    }

    /**
     * Removes the equivalent object from the list.
     * The object must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new IdentityNotFoundException();
        }
    }

    public void setList(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code objects}.
     * {@code objects} must not contain duplicate objects.
     */
    public void setList(List<T> objects) {
        requireAllNonNull(objects);
        if (!objectsAreUnique(objects)) {
            throw new DuplicateIdentityException();
        }

        internalList.setAll(objects);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueList // instanceof handles nulls
                && internalList.equals(((UniqueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code objects} contains only unique objects.
     */
    private boolean objectsAreUnique(List<T> objects) {
        for (int i = 0; i < objects.size() - 1; i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                if (objects.get(i).isSameAs(objects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
