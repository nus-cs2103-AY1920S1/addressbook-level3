package seedu.address.logic.history;

//@@author ambervoong

import java.util.Stack;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Stores UndoableCommands that have been executed.
 */
public class CommandHistory {

    private Stack<UndoableCommand> historyStack;

    public CommandHistory() {
        historyStack =  new Stack<UndoableCommand>();
    }

    public void addExecutedCommand(UndoableCommand command) {
        historyStack.push(command);
    }

    public UndoableCommand getExecutedCommand() {
        return historyStack.pop();
    }
}
