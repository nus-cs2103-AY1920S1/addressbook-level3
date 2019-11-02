package mams.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class representing an iterable pointer to a List. Now adapted to take in any List of generic variable T,
 * makes a defensive copy of it internally, then returns an iterable pointer to the internal List.
 * Note that if the original list was modified, the old ListPointer object will no longer be valid since
 * it still points to an old internal copy of the list captured at the time of initialization.
 */
public class ListPointer<T> {
    // defensive copy of the list of interest.
    private List<T> list;
    private int index;

    /**
     * Constructs {@code ListPointer}, and initialize cursor
     * to point to the last element in {@code list}.
     */
    public ListPointer(List<T> list) {
        requireNonNull(list);
        this.list = new ArrayList<>(list);
        index = this.list.size() - 1;
    }

    /**
     * Appends {@code element} to the end of the list. We don't check for null
     * values here since this is meant as a generic multi-purpose container.
     * Future developers may need to store null-able values (even if the practice
     * is generally frowned upon).
     */
    public void add(T element) {
        list.add(element);
    }

    /**
     * Returns true if calling {@code #next()}, if next element exists
     * ie. calling {@code #next()} does not throw a {@code NoSuchElementException}.
     */
    public boolean hasNext() {
        int nextIndex = index + 1;
        return isWithinBounds(nextIndex);
    }

    /**
     * Returns true if calling {@code #previous()} if previous element exists
     * ie. calling {@code #previous()} does not throw a {@code NoSuchElementException}.
     */
    public boolean hasPrevious() {
        int previousIndex = index - 1;
        return isWithinBounds(previousIndex);
    }

    /**
     * Returns true if calling {@code #current()} if current element exists
     * ie. calling {@code #current()} does not throw a {@code NoSuchElementException}.
     */
    public boolean hasCurrent() {
        return isWithinBounds(index);
    }

    /**
     * Check if a given index is within bounds of the internal defensive copy.
     * @param index index to be checked against list
     * @return true if within bounds, false otherwise
     */
    private boolean isWithinBounds(int index) {
        return index >= 0 && index < list.size();
    }

    /**
     * Returns the next element in the list and advances the cursor position.
     * @throws NoSuchElementException if there is no more next element in the list.
     */
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(++index);
    }

    /**
     * Returns the previous element in the list and moves the cursor position backwards.
     * @throws NoSuchElementException if there is no more previous element in the list.
     */
    public T previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return list.get(--index);
    }

    /**
     * Returns the current element in the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public T current() {
        if (!hasCurrent()) {
            throw new NoSuchElementException();
        }
        return list.get(index);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListPointer)) {
            return false;
        }

        // state check
        ListPointer iterator = (ListPointer) other;
        return list.equals(iterator.list) && index == iterator.index;
    }
}
