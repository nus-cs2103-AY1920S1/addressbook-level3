package io.xpire.model.history;
import io.xpire.model.state.StackManager;
/**
 * A history that contains elements that are undoable (can be reversed or fast-forwarded with Undo/Redo Command).
 * @@author Kalsyc
 */
public class UndoableHistoryManager<T> extends History<T> {

    private int currentIndex = 0;
    private int headIndex = 0;

    /**
     * Retrieves the previous Element and sets the current index back by 1.
     *
     * @return previous Element.
     */
    public T previous() {
        currentIndex -= 1;
        return history.get(currentIndex);
    }

    /**
     * Retrieves the next Element and sets the current index forwards by 1.
     *
     * @return next Element.
     */
    public T next() {
        T nextElement = history.get(currentIndex);
        currentIndex += 1;
        return nextElement;
    }

    /**
     * Adds element to history.
     *
     * @param element Element to be added to history.
     */
    public void save(T element) {
        if (headIndex != currentIndex) {
            for (int i = 0; i < headIndex - currentIndex; i++) {
                history.removeLast();
            }
        }
        if (currentIndex == StackManager.MAXIMUM_STATES) {
            history.removeFirst();
            currentIndex -= 1;
        }
        history.add(element);
        currentIndex += 1;
        headIndex = currentIndex;
    }
}
