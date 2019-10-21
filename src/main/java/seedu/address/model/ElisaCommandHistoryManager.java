package seedu.address.model;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UndoableCommand;


/**
 * Stores the stack of all application states with current state at the top
 */

public class ElisaCommandHistoryManager implements ElisaCommandHistory {
    private Stack<UndoableCommand> undoStack;

    public ElisaCommandHistoryManager() {
        undoStack = new Stack<>();
    }

    /**
     * push a command
     * */
    @Override
    public void pushCommand(Command command) {
        if (command instanceof UndoableCommand) {
            undoStack.push((UndoableCommand) command);
        }
    }

    /**
     * pop last command
     * */
    @Override
    public UndoableCommand popCommand() {
        return undoStack.pop();
    }

    /**
     * peek last command
     * */
    @Override
    public UndoableCommand peekCommand() {
        return undoStack.peek();
    }

    /**
     * return stack size
     * */
    public int size() {
        return undoStack.size();
    }
}
