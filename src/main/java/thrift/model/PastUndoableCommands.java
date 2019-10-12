package thrift.model;

import java.util.Stack;

import thrift.logic.commands.Undoable;

/**
 * Stores all the past undoable commands executed by the user.
 */
public class PastUndoableCommands {

    private final Stack<Undoable> undoStack;
    private final Stack<Undoable> redoStack;

    public PastUndoableCommands() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Keeps track of undoable command. <br>
     * NOTE: You cannot redo undone commands once new undoable command is tracked.
     *
     * @param command is the undoable command to be tracked
     */
    public void addPastCommand(Undoable command) {
        if (hasRedoCommand()) {
            clearRedoStack();
        }
        undoStack.push(command);
    }

    /**
     * Retrieves undoable command to perform undo.
     *
     * @return retrieved undoable command
     */
    public Undoable getCommandToUndo() {
        Undoable undoCommand = undoStack.pop();
        addUndoneCommand(undoCommand);
        return undoCommand;
    }

    /**
     * Checks if there is any available command to undo.
     *
     * @return true if there is available commands to perform undo, else false.
     */
    public boolean hasUndoCommand() {
        return !undoStack.isEmpty();
    }

    /**
     * Removes all undoable commands to be redone.
     */
    private void clearRedoStack() {
        redoStack.clear();
    }

    /**
     * Keeps track of undone command.
     *
     * @param command is the undoable command to be tracked
     */
    private void addUndoneCommand(Undoable command) {
        redoStack.push(command);
    }

    /**
     * Retrieves undone command to perform redo.
     *
     * @return retrieved undone command
     */
    public Undoable getCommandToRedo() {
        Undoable redoCommand = redoStack.pop();
        undoStack.push(redoCommand);
        return redoCommand;
    }

    /**
     * Checks if there is any available command to redo.
     *
     * @return true if there is available commands to perform redo, else false.
     */
    public boolean hasRedoCommand() {
        return !redoStack.isEmpty();
    }
}
