package io.xpire.model.history;

/**
 * Generic interface that provides methods to save current status and retrieve previous/next
 * elements in HistoryManager.
 * @@author Kalsyc Xiaoyu
 * @param <T> Type to be stored in HistoryManager.
 */
public interface HistoryManager<T> {

    /**
     * Retrieves the previous Element of type {@Code T}.
     */
    T previous();

    /**
     * Retrieves the next Element of type {@Code T}.
     */
    T next();

    /**
     * Saves the element {@Code T} in the history.
     */
    void save(T element);

}
