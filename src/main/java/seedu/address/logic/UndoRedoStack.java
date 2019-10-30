package seedu.address.logic;

import java.util.LinkedList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoableCommand;

/**
 * Undo-redo Stack
 * @@author yamgent
 * Reused from https://github.com/se-edu/addressbook-level4/pull/610/files with minor modifications
 *
 */
public class UndoRedoStack {

    private static UndoRedoStack undoRedoStack = new UndoRedoStack();

    private LinkedList<UndoableCommand> undoStack;
    private LinkedList<UndoableCommand> redoStack;

    public UndoRedoStack() {
        undoStack = new LinkedList<>();
        redoStack = new LinkedList<>();
    }

    public UndoRedoStack(UndoRedoStack otherUndoRedoStack) {
        undoStack = otherUndoRedoStack.undoStack;
        redoStack = otherUndoRedoStack.redoStack;
    }

    /**
     * Pushes {@code command} onto the undo-stack if it is of type {@code UndoableCommand}. Clears the redo-stack
     * if {@code command} is not of type {@code UndoCommand} or {@code RedoCommand}.
     */
    public void push(Command command) {
        if (!(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
            redoStack.clear();
        }

        if (!(command instanceof UndoableCommand)) {
            return;
        }

        undoStack.addFirst((UndoableCommand) command);
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be undone in the stack.
     */
    public UndoableCommand popUndo() {
        UndoableCommand toUndo = undoStack.removeFirst();
        redoStack.addFirst(toUndo);
        return toUndo;
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be redone in the stack.
     */
    public UndoableCommand popRedo() {
        UndoableCommand toRedo = redoStack.removeFirst();
        undoStack.addFirst(toRedo);
        return toRedo;
    }

    /**
     * Returns true if there are more commands that can be undone.
     */
    public boolean canUndo() {
        return undoStack.size() > 0;
    }

    /**
     * Returns true if there are more commands that can be redone.
     */
    public boolean canRedo() {
        return redoStack.size() > 0;
    }

    public static UndoRedoStack getUndoRedoStack() {
        return undoRedoStack;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndoRedoStack)) {
            return false;
        }

        UndoRedoStack stack = (UndoRedoStack) other;

        // state check
        return undoStack.equals(stack.undoStack)
                && redoStack.equals(stack.redoStack);
    }
}
