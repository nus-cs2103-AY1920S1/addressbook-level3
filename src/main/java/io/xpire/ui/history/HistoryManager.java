package io.xpire.ui.history;

import java.util.ArrayDeque;
import java.util.Stack;
import static java.util.Objects.requireNonNull;

public class HistoryManager {

    public static final int MAX_RETRIEVAL_SIZE = 20;
    private static ArrayDeque<String> retrievalStack = new ArrayDeque<>();
    private static Stack<String> redoStack = new Stack<>();

    private String current;

    public String previous() {
        if (isRetrievalStackEmpty()) {
            return null;
        } else {
            requireNonNull(current);
            redoStack.push(current);
            current = retrievalStack.pop();
            return current;
        }
    }

    public String next() {
        retrievalStack.push(current);
        if (isRedoStackEmpty()) {
            return null;
        }
        requireNonNull(current);
        current = redoStack.pop();
        return current;
    }

    public void save(String input) {
        this.current = input;
        redoStack.clear();
        retrievalStack.push(current);
        if (retrievalStack.size() > this.MAX_RETRIEVAL_SIZE) {
            retrievalStack.removeLast();
        }
    }

    /**
     * Returns if the retrievalStack is empty.
     */
    public boolean isRetrievalStackEmpty() {
        return this.retrievalStack.isEmpty();
    }

    /**
     * Returns if the redoStack is empty.
     */
    public boolean isRedoStackEmpty() {
        return this.redoStack.isEmpty();
    }
}
