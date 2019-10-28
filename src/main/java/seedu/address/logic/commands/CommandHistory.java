package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Predicate;

/**
 * Append-only storage of the user's command history and allows for bidirectional navigation of the history.
 */
public class CommandHistory extends ArrayList<String> {
    private IndexedBasedListIterator<String> listIterator = (IndexedBasedListIterator<String>) listIterator();

    public String getPreviousCommand() {
        return listIterator.hasPrevious() ? listIterator.previous() : null;
    }

    public String getNextCommand() {
        return listIterator.hasNext() ? listIterator.next() : null;
    }

    public void resetHistoryPointer() {
        listIterator.repositionIndexToEnd();
    }

    @Override
    public ListIterator<String> listIterator() {
        return new IndexedBasedListIterator<>(this);
    }

    @Override
    public boolean add(final String newElement) {
        Objects.requireNonNull(newElement);
        if (!isEmpty() && get(size() - 1).equals(newElement)) {
            // the command being added is exactly the same as the most recent command, so we'll skip it
            return false;
        }

        final boolean result = super.add(newElement);
        resetHistoryPointer();
        return result;
    }

    @Override
    public void add(final int index, final String newElement) {
        Objects.requireNonNull(newElement);
        if (index != size()) {
            throw new UnsupportedOperationException();
        }

        this.add(newElement);
    }

    @Override
    public boolean addAll(final Collection<? extends String> c) {
        Objects.requireNonNull(c);
        final boolean result = super.addAll(c);
        resetHistoryPointer();
        return result;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super String> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    /**
     * A custom ListIterator class that allows for very basic concurrent modification of the the underlying list.
     */
    static class IndexedBasedListIterator<E> implements ListIterator<E> {
        private final List<E> list;
        private int index = -1;

        <RandomAccessList extends List<E> & RandomAccess> IndexedBasedListIterator(final RandomAccessList list) {
            this.list = Objects.requireNonNull(list);
        }

        @Override
        public boolean hasNext() {
            return index + 1 < list.size();
        }

        @Override
        public E next() {
            final int i = nextIndex();
            if (i >= list.size()) {
                throw new NoSuchElementException();
            }
            index = i;
            return list.get(index);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            final int i = previousIndex();
            if (i < 0) {
                throw new NoSuchElementException();
            }
            index = i;
            return list.get(index);
        }

        @Override
        public int nextIndex() {
            return Math.min(index + 1, list.size());
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E s) {
            throw new UnsupportedOperationException();
        }

        /**
         * Repositions the internal index.
         * This is used to set the internal index to -1 if it's desirable for {@link #next()} to return the first value.
         * Also accepts the size of the underlying list so that {@link #previous()} will return the last list value.
         *
         * @param newIdx New value for the internal index.
         */
        public void repositionIndex(final int newIdx) {
            if (newIdx < -1 || list.size() < newIdx) {
                throw new IndexOutOfBoundsException();
            }

            index = newIdx;
        }

        /**
         * Repositions the internal index so that {@link #next()} returns the first value of the list.
         */
        public void repositionIndexToStart() {
            repositionIndex(-1);
        }

        /**
         * Repositions the internal index so that {@link #previous()} returns the last value of the list.
         */
        public void repositionIndexToEnd() {
            repositionIndex(list.size());
        }
    }
}
