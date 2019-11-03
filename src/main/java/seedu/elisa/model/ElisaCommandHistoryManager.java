package seedu.elisa.model;

import java.util.Stack;

import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.commands.UndoableCommand;


/**
 * Stores the stack of all application states with current state at the top
 */

public class ElisaCommandHistoryManager implements ElisaCommandHistory {
    private Stack<UndoableCommand> undoStack;
    private Stack<UndoableCommand> redoStack;

    public ElisaCommandHistoryManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * push a command into undo stack
     * */
    @Override
    public void pushUndo(Command command) {
        if (command instanceof UndoableCommand) {
            undoStack.push((UndoableCommand) command);
        }
    }

    /**
     * empty the redo stack (for use when new command is executed)
     * */
    @Override
    public void clearRedo() {
        redoStack.clear();
    }

    /**
     * pop last command from undo stack
     * */
    @Override
    public UndoableCommand popUndo() {
        UndoableCommand command = undoStack.pop();
        pushRedo(command);
        return command;
    }

    /**
     * peek last command from undo stack
     * */
    @Override
    public UndoableCommand peekUndo() {
        return undoStack.peek();
    }

    /**
     * return stack size of undo stack
     * */
    @Override
    public int sizeUndo() {
        return undoStack.size();
    }

    @Override
    public void pushRedo(Command command) {
        if (command instanceof UndoableCommand) {
            redoStack.push((UndoableCommand) command);
        }
    }

    @Override
    public UndoableCommand popRedo() {
        UndoableCommand command = redoStack.pop();
        pushUndo(command);
        return command;
    }

    @Override
    public UndoableCommand peekRedo() {
        return redoStack.peek();
    }

    @Override
    public int sizeRedo() {
        return redoStack.size();
    }
}
