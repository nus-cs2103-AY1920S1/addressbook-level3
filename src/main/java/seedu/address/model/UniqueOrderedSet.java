package seedu.address.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import seedu.address.model.exceptions.DuplicateElementException;

/**
 * Represents a set of non-duplicate, non-null values.
 * Set is ordered by insertion order.
 * Only supports adding elements.
 * A null pointer exception will be thrown if a null value is added to this set.
 * A duplicate element exception will be throw if a duplicate element is added to this set.
 */
public class UniqueOrderedSet<E> implements Iterable<E> {

    private final LinkedHashSet<E> set;

    public UniqueOrderedSet() {
        this.set = new LinkedHashSet<>();
    }

    /**
     * Adds an element to this data structure.
     * @param e the element to add
     */
    public void add(E e) {
        Objects.requireNonNull(e);
        if (set.contains(e)) {
            throw new DuplicateElementException();
        }
        set.add(e);
    }

    public List<E> toUnmodifiableList() {
        return List.copyOf(set);
    }

    @Override
    public Iterator<E> iterator() {
        return this.set.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UniqueOrderedSet) {
            UniqueOrderedSet u = (UniqueOrderedSet) o;
            return Objects.equals(this.set, u.set);
        }
        return false;
    }
}
