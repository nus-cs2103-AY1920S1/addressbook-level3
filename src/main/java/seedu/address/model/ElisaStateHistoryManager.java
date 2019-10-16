package seedu.address.model;

import java.util.Stack;

/**
 * Stores the stack of all application states with current state at the top
 */

public class ElisaStateHistoryManager implements ElisaStateHistory {
    private Stack<ElisaState> undoStack;

    public ElisaStateHistoryManager() {
        undoStack = new Stack<>();
    }

    @Override
    public void pushCommand(ElisaState currentState) {
        undoStack.push(currentState.deepCopy());
    }

    @Override
    public ElisaState popCommand() {
        if (undoStack.size() > 1) {
            return undoStack.pop();
        } else {
            return null;
        }
    }

    @Override
    public ElisaState peekCommand() {
        return undoStack.peek().deepCopy();
    }

    public int size() {
        return undoStack.size();
    }
}
