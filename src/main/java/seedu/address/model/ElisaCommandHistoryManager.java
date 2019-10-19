package seedu.address.model;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.UndoCommand;

import java.util.Stack;

/**
 * Stores the stack of all application states with current state at the top
 */

public class ElisaCommandHistoryManager implements ElisaCommandHistory {
    private Stack<Command> undoStack;

    public ElisaCommandHistoryManager() {
        undoStack = new Stack<>();
    }

    @Override
    public void pushCommand(Command command) {
        if (!(command instanceof UndoCommand || command instanceof ExitCommand || command instanceof ShowCommand)){
            undoStack.push(command);
        }
    }

    @Override
    public Command popCommand() {
        if (undoStack.size() > 0) {
            return undoStack.pop();
        } else {
            return null;
        }
    }

    @Override
    public Command peekCommand() {
        return undoStack.peek();
    }

    public int size() {
        return undoStack.size();
    }
}
