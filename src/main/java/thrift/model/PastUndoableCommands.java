package thrift.model;

import static java.util.Objects.requireNonNull;

import java.util.Stack;
import java.util.logging.Logger;

import thrift.commons.core.LogsCenter;
import thrift.logic.commands.RedoCommand;
import thrift.logic.commands.UndoCommand;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.exceptions.CommandException;

/**
 * Stores all the past undoable commands executed by the user.
 */
public class PastUndoableCommands {

    private final Stack<Undoable> undoStack;
    private final Stack<Undoable> redoStack;
    private final Logger logger = LogsCenter.getLogger(PastUndoableCommands.class);

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
        requireNonNull(command);
        if (hasRedoCommand()) {
            clearRedoStack();
        }
        undoStack.push(command);
        logger.fine("A new undoable command is available to perform undo.");
    }

    /**
     * Retrieves undoable command to perform undo.
     *
     * @return retrieved undoable command
     * @throws CommandException if there is no command to undo.
     */
    public Undoable getCommandToUndo() throws CommandException {
        if (hasUndoCommand()) {
            Undoable undoCommand = undoStack.pop();
            addUndoneCommand(undoCommand);
            return undoCommand;
        }
        throw new CommandException(UndoCommand.NO_UNDOABLE_COMMAND);
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
        logger.fine("All commands available for redo have been purged.");
        redoStack.clear();
    }

    /**
     * Keeps track of undone command.
     *
     * @param command is the undoable command to be tracked
     */
    private void addUndoneCommand(Undoable command) {
        requireNonNull(command);
        redoStack.push(command);
        logger.fine("A new undoable command is available to perform redo.");
    }

    /**
     * Retrieves undone command to perform redo.
     *
     * @return retrieved undone command
     * @throws CommandException if there is no command to redo.
     */
    public Undoable getCommandToRedo() throws CommandException {
        if (hasRedoCommand()) {
            Undoable redoCommand = redoStack.pop();
            undoStack.push(redoCommand);
            return redoCommand;
        }
        throw new CommandException(RedoCommand.NO_REDOABLE_COMMAND);
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
