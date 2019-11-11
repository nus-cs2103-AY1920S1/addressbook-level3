package io.xpire.model.state;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * A class that wraps two internal stacks that handles Undo/Redo commands.
 * @@author Kalsyc
 */
public class StackManager implements StateManager {

    public static final int MAXIMUM_STATES = 10;
    private static State current;
    private final ArrayDeque<State> undoStack = new ArrayDeque<>();
    private final Stack<State> redoStack = new Stack<>();

    /**
     * Processes the undo command by pushing the current state into the redoStack and
     * returning the most recent State.
     *
     * @param currentState current State.
     * @return popped State (most recent State).
     */
    public State undo(State currentState) {
        if (isNotUndoable()) {
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
    public State redo() {
        undoStack.push(current);
        if (isNotRedoable()) {
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
    public void saveState(State currentState) {
        redoStack.clear();
        undoStack.push(currentState);
        if (undoStack.size() > MAXIMUM_STATES) {
            undoStack.removeLast();
        }
    }

    /**
     * Returns if the undoStack is empty.
     */
    public boolean isNotUndoable() {
        return this.undoStack.isEmpty();
    }

    /**
     * Returns if the redoStack is empty.
     */
    public boolean isNotRedoable() {
        return this.redoStack.isEmpty();
    }

}
