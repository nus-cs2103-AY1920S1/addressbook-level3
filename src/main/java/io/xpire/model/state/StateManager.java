package io.xpire.model.state;

/**
 * A class that handles States accordingly and wraps around StackManager.
 */
public class StateManager {

    public static final int MAX_STACK_SIZE = 10;
    private StackManager stackManager = new StackManager();

    public void saveState(State state) {
        stackManager.saveState(state);
    }

    public boolean isRedoStackEmpty() {
        return stackManager.isRedoStackEmpty();
    }

    public boolean isUndoStackEmpty() {
        return stackManager.isUndoStackEmpty();
    }

    public State undo(State state) {
        return stackManager.undo(state);
    }

    public State redo() {
        return stackManager.redo();
    }

}
