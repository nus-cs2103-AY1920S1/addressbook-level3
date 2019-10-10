package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.exceptions.ElementNotFoundException;


/**
 * A list that enforces uniqueness between its elements and does not allow nulls.
 * An element is considered unique by comparing using {@code T#isSameElement(T)}. As such, adding and updating of
 * element uses T#isSameElement(T) for equality so as to ensure that the element being added, removal and updated is
 * unique in terms of identity in the UniqueElementList.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueElementList<T extends UniqueElement> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameElement);
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateElementException(toAdd);
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the element {@code target} in the list with {@code editedElement}.
     * {@code target} must exist in the list.
     * The identity of {@code editedElement} must not be the same as another existing element in the list.
     */
    public void setElement(T target, T editedElement) {
        requireAllNonNull(target, editedElement);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ElementNotFoundException(target);
        }

        if (!target.isSameElement(editedElement) && contains(editedElement)) {
            throw new DuplicateElementException(editedElement);
        }

        internalList.set(index, editedElement);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ElementNotFoundException(toRemove);
        }
    }

    public void setElements(UniqueElementList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code elements}.
     * {@code elements} must not contain duplicate elements.
     */
    public void setElements(List<T> elements) {
        requireAllNonNull(elements);
        if (elements.size() > 0 && !elementsAreUnique(elements)) {
            throw new DuplicateElementException(elements.get(0));
        }

        internalList.setAll(elements);
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
                || (other instanceof UniqueElementList // instanceof handles nulls
                && internalList.equals(((UniqueElementList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code elements} contains only unique elements.
     */
    private boolean elementsAreUnique(List<T> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (elements.get(i).isSameElement(elements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
