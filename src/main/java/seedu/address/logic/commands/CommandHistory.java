package seedu.address.logic.commands;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Stores the user's command history and allows for bidirectional navigation of the history.
 */
public class CommandHistory extends ArrayList<String> {
    private List<WeakReference<CustomListIterator>> customListIterators = new LinkedList<>();
    private ListIterator<String> listIterator = listIterator();

    public String getPreviousCommand() {
        return listIterator.hasNext() ? listIterator.next() : null;
    }

    public String getNextCommand() {
        return listIterator.hasPrevious() ? listIterator.previous() : null;
    }

    public void resetHistoryPointer() {
        listIterator = listIterator();
    }

    @Override
    public ListIterator<String> listIterator() {
        final CustomListIterator listIterator = new CustomListIterator(this);

        /*
         A WeakReference is used so that the lifetime of the created CustomListIterator is determined by this method's
         consumer rather than this instance forever preventing all CustomListIterator from being garbage collected.
        */
        customListIterators.add(new WeakReference<>(listIterator));
        return listIterator;
    }

    @Override
    public void add(final int index, final String newElement) {
        if (index != 0) {
            throw new UnsupportedOperationException("Index must be 0");
        }

        if (!isEmpty() && get(0).equals(newElement)) {
            // the command being added is exactly the same as the most recent command, so we'll skip it
            return;
        }

        super.add(index, newElement);

        /*
         Since a new element is added to the list, we need to increment the index
         of all ListIterators so that they still point to the correct previous/next elements
        */
        final Iterator<WeakReference<CustomListIterator>> customListIteratorsIterator = customListIterators.iterator();
        while (customListIteratorsIterator.hasNext()) {
            final WeakReference<CustomListIterator> customListIteratorReference = customListIteratorsIterator.next();
            final CustomListIterator customListIterator = customListIteratorReference.get();

            if (customListIterator == null) {
                // The CustomListIterator has been garbage collected, so we'll remove the WeakReference container.
                customListIteratorsIterator.remove();
            } else {
                customListIterator.incrementIndex();
            }
        }
    }

    /**
     * A custom ListIterator class that allows for very basic concurrent modification of the the underlying list.
     */
    static class CustomListIterator implements ListIterator<String> {
        private final ArrayList<String> backingStore;
        private int index = -1;

        CustomListIterator(final ArrayList<String> backingStore) {
            this.backingStore = backingStore;
        }

        private boolean isWithinBounds(final int index) {
            return 0 <= index && index < backingStore.size();
        }

        private void incrementIndex() {
            index++;
        }

        @Override
        public boolean hasNext() {
            final int nextIndex = index + 1;
            return isWithinBounds(nextIndex);
        }

        @Override
        public String next() {
            index = index + 1;
            return backingStore.get(index);
        }

        @Override
        public boolean hasPrevious() {
            final int previousIndex = index - 1;
            return isWithinBounds(previousIndex);
        }

        @Override
        public String previous() {
            index = index - 1;
            return backingStore.get(index);
        }

        @Override
        public int nextIndex() {
            final int nextIndex = index + 1;
            if (isWithinBounds(nextIndex)) {
                return nextIndex;
            } else {
                return backingStore.size();
            }
        }

        @Override
        public int previousIndex() {
            final int previousIndex = index - 1;
            if (isWithinBounds(previousIndex)) {
                return previousIndex;
            } else {
                return -1;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(String s) {
            throw new UnsupportedOperationException();
        }
    }
}
