package io.xpire.model;

import java.util.Stack;

import io.xpire.model.state.State;

/**
 * A class that wraps two internal stacks that handles Undo/Redo commands.
 */
public class StackManager {

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
        redoStack.push(currentState);
        if (isUndoStackEmpty()) {
            return null;
        }
        return undoStack.pop();
    }

    /**
     * Processes the redo comman by pushing the current state into the undoStack and
     * returning the state before the previous Undo.
     *
     * @param currentState current State.
     * @return popped State (previous State).
     */
    public State redo(State currentState) {
        undoStack.push(currentState);
        if (isRedoStackEmpty()) {
            return null;
        }
        return redoStack.pop();
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
