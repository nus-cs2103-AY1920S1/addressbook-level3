package seedu.address.logic.history;

import java.util.Deque;
import java.util.LinkedList;

import seedu.address.logic.commands.UndoableCommand;

//@@author ambervoong
/**
 * Stores UndoableCommands that have been executed.
 */
public class CommandHistory {
    public static final int MAX_SIZE = 10;
    private Deque<UndoableCommand> history;

    public CommandHistory() {
        history = new LinkedList<>();
    }

    /**
     * Adds an executed command to the CommandHistory deque.
     * @param command an executed command.
     */
    public void addExecutedCommand(UndoableCommand command) {
        if (history.size() >= MAX_SIZE) {
            history.pollLast();
        }
        history.push(command);
    }

    /**
     * Gets the last executed command from the CommandHistory deque.
     * @return the last executed command.
     */
    public UndoableCommand getExecutedCommand() {
        return history.poll();
    }

    /**
     * Clears all entries in history.
     */
    public void clear() {
        history.clear();
    }

    /**
     * Gets the current size of the CommandHistory deque.
     * @return an int representing the size of the deque.
     */
    public int getSize() {
        return history.size();
    }
}
