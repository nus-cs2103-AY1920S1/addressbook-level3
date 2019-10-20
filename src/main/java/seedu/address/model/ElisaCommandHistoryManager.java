package seedu.address.model;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.UndoCommand;


/**
 * Stores the stack of all application states with current state at the top
 */

public class ElisaCommandHistoryManager implements ElisaCommandHistory {
    private Stack<Command> undoStack;

    public ElisaCommandHistoryManager() {
        undoStack = new Stack<>();
    }

    /**
     * push a command
     * */
    @Override
    public void pushCommand(Command command) {
        //hema: allow show to be pushed after fixing it
        if (!(command instanceof UndoCommand || command instanceof ExitCommand || command instanceof ShowCommand)) {
            undoStack.push(command);
        }
    }

    /**
     * pop last command
     * */
    @Override
    public Command popCommand() {
        if (undoStack.size() > 0) {
            return undoStack.pop();
        } else {
            return null;
        }
    }

    /**
     * peek last command
     * */
    @Override
    public Command peekCommand() {
        return undoStack.peek();
    }

    /**
     * return stack size
     * */
    public int size() {
        return undoStack.size();
    }
}
