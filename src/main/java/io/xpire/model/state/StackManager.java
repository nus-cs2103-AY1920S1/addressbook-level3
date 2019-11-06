package io.xpire.model.state;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * A class that wraps two internal stacks that handles Undo/Redo commands.
 */
public class StackManager {

    private static State current;
    private ArrayDeque<State> undoStack = new ArrayDeque<>();
    private Stack<State> redoStack = new Stack<>();

    /**
     * Processes the undo command by pushing the current state into the redoStack and
     * returning the most recent State.
     *
     * @param currentState current State.
     * @return popped State (most recent State).
     */
    State undo(State currentState) {
        if (isUndoStackEmpty()) {
            return null;
        }
        redoStack.push(currentState);
        current = undoStack.pop();
        return current;
    }

    /**
     * Processes the redo command by pushing the current state into the undoStack and
     * returning the state before the previous Undo.
     *
     * @return popped State (previous State).
     */
    State redo() {
        undoStack.push(current);
        if (isRedoStackEmpty()) {
            return null;
        }
        current = redoStack.pop();
        return current;
    }

    /**
     * Saves the input State.
     *
     * @param currentState current State to be saved.
     */
    void saveState(State currentState) {
        redoStack.clear();
        undoStack.push(currentState);
        if (undoStack.size() > StateManager.MAX_STACK_SIZE) {
            undoStack.removeLast();
        }
    }

    /**
     * Returns if the undoStack is empty.
     */
    public boolean isUndoStackEmpty() {
        return this.undoStack.isEmpty();
    }

    /**
     * Returns if the redoStack is empty.
     */
    public boolean isRedoStackEmpty() {
        return this.redoStack.isEmpty();
    }

}
