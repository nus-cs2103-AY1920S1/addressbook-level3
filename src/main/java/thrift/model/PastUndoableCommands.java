package thrift.model;

import java.util.Stack;

import thrift.logic.commands.Undoable;

/**
 * Stores all the past undoable commands executed by the user.
 */
public class PastUndoableCommands {

    private final Stack<Undoable> undoStack;

    public PastUndoableCommands() {
        this.undoStack = new Stack<>();
    }

    public void addPastCommand(Undoable command) {
        undoStack.push(command);
    }

    public Undoable getCommandToUndo() {
        return undoStack.pop();
    }

    public boolean isEmpty() {
        return undoStack.isEmpty();
    }
}
