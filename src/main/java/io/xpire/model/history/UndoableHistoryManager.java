package io.xpire.model.history;

import java.util.LinkedList;

/**
 * A history that contains elements that are undoable (can be reversed or fast-forwarded with Undo/Redo Command).
 * @@author Kalsyc
 */
public class UndoableHistoryManager<T> implements HistoryManager<T> {

    private LinkedList<T> history = new LinkedList<>();
    private int currentIndex = 0;
    private int headIndex = 0;
    private int maximum;

    public UndoableHistoryManager(int maximum) {
        this.maximum = maximum;
    }

    /**
     * Retrieves the previous Element and sets the current index back by 1.
     *
     * @return previous Element.
     */
    @Override
    public T previous() {
        assert currentIndex > 0;
        currentIndex -= 1;
        return history.get(currentIndex);
    }

    /**
     * Retrieves the next Element and sets the current index forwards by 1.
     * Returns head if currentIndex is more than or equals to headIndex.
     *
     * @return next Element.
     */
    @Override
    public T next() {
        assert currentIndex >= 0;
        if (currentIndex >= headIndex) {
            return history.get(headIndex - 1);
        }
        T nextElement = history.get(currentIndex);
        currentIndex += 1;
        return nextElement;
    }

    /**
     * Adds element to history.
     *
     * @param element Element to be added to history.
     */
    @Override
    public void save(T element) {
        if (headIndex != currentIndex) {
            for (int i = 0; i < headIndex - currentIndex; i++) {
                history.removeLast();
            }
        }
        if (currentIndex == maximum) {
            history.removeFirst();
            currentIndex -= 1;
        }
        history.add(element);
        currentIndex += 1;
        headIndex = currentIndex;
        assert headIndex >= 0;
    }
}
