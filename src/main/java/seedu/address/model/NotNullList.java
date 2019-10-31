package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import seedu.address.model.events.exceptions.EventNotFoundException;

/**
 * Represents a list of non-null values.
 * A null pointer exception will be thrown if a null value is added to this list.
 */
public class NotNullList<E> implements Iterable<E> {

    private final List<E> list;

    public NotNullList() {
        this.list = new ArrayList<>();
    }

    /**
     * Adds an element to this list.
     * @param e the element to add
     */
    public void add(E e) {
        requireNonNull(e);
        this.list.add(e);
    }

    /**
     * Check if an element is contained in this list.
     * @param e the element to check
     * @return whether or not the element is contained in this list
     */
    public boolean contains(E e) {
        requireNonNull(e);
        return list.contains(e);
    }

    /**
     * Removes an element from this list.
     * @param e the element to remove
     */
    public void remove(E e) {
        requireNonNull(e);
        if (!list.remove(e)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Replace an element in this list with another element.
     * @param e the element to replace
     * @param r the replacement element
     */
    public void replace(E e, E r) {
        requireAllNonNull(e, r);

        int i = list.indexOf(e);
        if (i == -1) {
            throw new EventNotFoundException();
        }

        list.set(i, r);
    }

    /**
     * Clears this list and adds elements from a given list.
     * @param list the list of elements to re-add
     */
    public void reset(List<E> list) {
        requireNonNull(list);
        this.list.clear();
        for (E e : list) {
            this.add(e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof NotNullList) {
            NotNullList e = (NotNullList) object;
            return Objects.equals(this.list, e.list);
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return this.list.iterator();
    }
}
