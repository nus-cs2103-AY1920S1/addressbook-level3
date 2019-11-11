package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is used to point to an element in the history list and iterate through the list.
 */
public class HistoryPointer {
    private List<String> historyLists;
    private int index;

    /**
     * Constructs {@code HistoryPointer} which is backed by a defensive copy of {@code list}.
     * The cursor points to the last element in {@code list}.
     */
    public HistoryPointer(List<String> list) {
        this.historyLists = new ArrayList<>(list);
        index = this.historyLists.size() - 1;
    }

    /**
     * Appends {@code element} to the end of the list.
     */
    public void add(String history) {
        historyLists.add(history);
    }

    /**
     * Returns true if calling {@code #next()} does not throw a {@code NoSuchElementException}.
     */
    public boolean hasNext() {
        int nextIndex = index + 1;
        return isWithinBounds(nextIndex);
    }

    /**
     * Returns true if calling {@code #previous()} does not throw a {@code NoSuchElementException}.
     */
    public boolean hasPrevious() {
        int previousIndex = index - 1;
        return isWithinBounds(previousIndex);
    }

    /**
     * Returns true if calling {@code #current()} does not throw a {@code NoSuchElementException}.
     */
    public boolean hasCurrent() {
        return isWithinBounds(index);
    }

    private boolean isWithinBounds(int index) {
        return index >= 0 && index < historyLists.size();
    }

    /**
     * Points to the next element in the list and advances the cursor position.
     * @throws NoSuchElementException if there is no more next element in the list.
     */
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return historyLists.get(++index);
    }

    /**
     * Points the previous element in the list and moves the cursor position backwards.
     * @throws NoSuchElementException if there is no more previous element in the list.
     */
    public String previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return historyLists.get(--index);
    }

    /**
     * Returns the current element in the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public String current() {
        if (!hasCurrent()) {
            throw new NoSuchElementException();
        }
        return historyLists.get(index);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryPointer)) {
            return false;
        }

        // state check
        HistoryPointer iterator = (HistoryPointer) other;
        return historyLists.equals(iterator.historyLists) && index == iterator.index;
    }
}
