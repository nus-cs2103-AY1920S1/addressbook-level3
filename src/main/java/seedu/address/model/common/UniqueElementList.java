package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.exceptions.DuplicateEntryException;
import seedu.address.model.exceptions.EntryNotFoundException;


/**
 * A list of elements that enforces uniqueness between its elements and does not allow nulls.
 * A element is considered unique by comparing using {@code Element#isSameAs(Element)}. As such, adding and updating of
 * elements uses Element#isSameElement(Element) for equality so as to ensure that the element being added or updated is
 * unique in terms of identity in the UniqueElementList. However, the removal of a element uses Element#equals(Object)
 * so as to ensure that the element with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueElementList<T extends Identical> implements Iterable<T> {

    protected final ObservableList<T> internalList = FXCollections.observableList(new UniqueTreeList<>());
    protected final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the list contains an element who equals to the given argument.
     */
    public boolean containsExact(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().filter(toCheck::isSameAs).anyMatch(toCheck::equals);
    }

    /**
     * Adds a element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEntryException();
        }
        internalList.add(toAdd);
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the element {@code target} in the list with {@code editedElement}.
     * {@code target} must exist in the list.
     * The identity of {@code editedElement} must not be the same as another existing element in the list.
     */
    public void set(T target, T editedElement) {
        requireAllNonNull(target, editedElement);

        if (target.compareTo(editedElement) != 0 && contains(editedElement)) {
            throw new DuplicateEntryException();
        }

        if (!internalList.remove(target)) {
            throw new EntryNotFoundException();
        }

        internalList.add(editedElement);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setAll(UniqueElementList replacement) {
        requireNonNull(replacement);
        internalList.clear();
        internalList.addAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code elements}.
     * {@code elements} must not contain duplicate elements.
     */
    public void setAll(List<T> elements) {
        requireAllNonNull(elements);
        if (!elementsAreUnique(elements)) {
            throw new DuplicateEntryException();
        }

        internalList.clear();
        internalList.addAll(elements);
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
        UniqueTreeList<T> treeList = new UniqueTreeList<>();
        for (T element : elements) {
            if (!treeList.add(element)) {
                return false;
            }
        }

        return true;
    }
}
