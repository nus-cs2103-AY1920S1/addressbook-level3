package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import seedu.address.model.exceptions.DuplicateElementException;

/**
 * Represents a list of non-duplicate, non-null values.
 * A null pointer exception will be thrown if a null value is added to this list.
 * A duplicate element exception will be throw if a duplicate element is added to this list.
 */
public class UniqueList<E> extends ArrayList<E> {

    public UniqueList() {
        super();
    }

    public UniqueList(Collection<? extends E> collection) {
        super(collection);
    }

    @Override
    public boolean add(E e) {
        requireNonNull(e);
        if (super.contains(e)) {
            throw new DuplicateElementException();
        }
        return super.add(e);
    }

    /**
     * Replace an element in this list with another element.
     * @param e the element to replace
     * @param r the replacement element
     */
    public void replace(E e, E r) {
        requireAllNonNull(e, r);

        int i = super.indexOf(e);
        if (i == -1) {
            throw new NoSuchElementException();
        }

        super.set(i, r);
    }
}
