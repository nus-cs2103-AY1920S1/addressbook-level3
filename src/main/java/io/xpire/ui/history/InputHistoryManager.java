package io.xpire.ui.history;

import java.util.LinkedList;

import io.xpire.model.history.HistoryManager;

/**
 * Stores user input String history. Written by xiaoyu-nus.
 * @@author xiaoyu-nus
 */

public class InputHistoryManager<String> implements HistoryManager<String> {

    private static final int MAX_RETRIEVAL_SIZE = 20;
    private int position = -1;
    private LinkedList<String> history = new LinkedList<>();

    /**
     * Retrieves a command entered after the current command.
     * @return a user input String. Returns {@Code null} if the current command is the most recent command.
     */
    public String next() {
        if (position < 1) {
            return null;
        } else {
            return history.get(--position);
        }
    }

    /**
     * Retrieves a command entered before the current command.
     * @return a user input String. Returns {@Code null} if the current command is the least recent command stored.
     */
    public String previous() {
        if (position >= history.size() - 1) {
            return null;
        } else {
            return history.get(++position);
        }
    }

    /**
     * Saves the user input String.
     * @param input user input String.
     */
    public void save(String input) {
        position = -1;
        history.offerFirst(input);
        if (history.size() > MAX_RETRIEVAL_SIZE) {
            history.pollLast();
        }
    }

    /**
     * Returns if the retrievalList is empty.
     */
    public boolean isRetrievalListEmpty() {
        return this.history.isEmpty();
    }
}
