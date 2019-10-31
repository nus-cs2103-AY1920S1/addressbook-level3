package io.xpire.model;

import java.util.Stack;

import io.xpire.model.state.State;

/**
 * A class that wraps two internal stacks that handles Undo/Redo commands.
 */
public class StackManager {

    private static State current;
    private Stack<State> undoStack = new Stack<>();
    private Stack<State> redoStack = new Stack<>();

    /**
     * Processes the undo command by pushing the current state into the redoStack and
     * returning the most recent State.
     *
     * @param currentState current State.
     * @return popped State (most recent State).
     */
    public State undo(State currentState) {
        if (isUndoStackEmpty()) {
            return null;
        }
        redoStack.push(currentState);
        current = undoStack.pop();
        return current;
    }

    public boolean isDateSorted() {
        return undoStack.peek().isSortByDate();
    }

    /**
     * Processes the redo command by pushing the current state into the undoStack and
     * returning the state before the previous Undo.
     *
     * @return popped State (previous State).
     */
    public State redo() {
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
    public void saveState(State currentState) {
        redoStack.clear();
        undoStack.push(currentState);
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
