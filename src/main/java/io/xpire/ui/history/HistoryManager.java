package io.xpire.ui.history;

import java.util.LinkedList;

/**
 * Stores user input String history.
 * One application is to have only one instance of this class.
 */
public class HistoryManager {

    public static final int MAX_RETRIEVAL_SIZE = 20;
    private static int noOfInstance = 0;
    private LinkedList<String> retrievalList = new LinkedList<>();
    private int position = 0;

    private HistoryManager() {
        // private constructor
        noOfInstance = 1;
    }

    /**
     * Returns a {@Code HistoryManager} object.
     * @return a {@Code HistoryManager} object.
     */
    public static HistoryManager getHistoryManager() {
        if (noOfInstance < 1) {
            return new HistoryManager();
        } else {
            return null;
        }
    }

    /**
     * Retrieves a command entered after the current command.
     * @return a user input String. Returns {@Code null} if the current command is the most recent command.
     */
    public String next() {
        if (retrievalList.size() < 1) {
            return null;
        }
        if (position < 0) {
            position = -1;
            return null;
        }
        if (position > retrievalList.size() - 1) {
            position = retrievalList.size() - 2;
            return retrievalList.get(position--);
        } else {
            return retrievalList.get(position--);
        }
    }

    /**
     * Retrieves a command entered before the current command.
     * @return a user input String. Returns {@Code null} if the current command is the least recent command stored.
     */
    public String previous() {
        if (position > retrievalList.size() - 1) {
            return null;
        } else if (position < 0) {
            position = 0;
            return retrievalList.get(position++);
        } else {
            return retrievalList.get(position++);
        }
    }

    /**
     * Saves the user input String.
     * @param input user input String.
     */
    public void save(String input) {
        position = 0;
        retrievalList.offerFirst(input);
        if (retrievalList.size() > MAX_RETRIEVAL_SIZE) {
            retrievalList.pollLast();
        }
    }

    /**
     * Returns if the retrievalList is empty.
     */
    public boolean isRetrievalListEmpty() {
        return this.retrievalList.isEmpty();
    }
}
