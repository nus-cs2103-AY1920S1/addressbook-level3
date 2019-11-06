package io.xpire.model.history;

import java.util.LinkedList;

import io.xpire.logic.commands.Command;

/**
 * A holder for all the commands.
 */
public class CommandHistory {

    private final LinkedList<Command> history = new LinkedList<>();
    private int currentIndex = 0;
    private int headIndex = 0;

    /**
     * Retrieves the previous Command and sets the current index back by 1.
     *
     * @return previous Command.
     */
    public Command retrievePreviousCommand() {
        currentIndex -= 1;
        return history.get(currentIndex);
    }

    /**
     * Retrieves the next Command and sets the current index forwards by 1.
     *
     * @return next Command.
     */
    public Command retrieveNextCommand() {
        Command nextCommand = history.get(currentIndex);
        currentIndex += 1;
        return nextCommand;
    }

    /**
     * Adds command to history.
     *
     * @param command Command to be added to history.
     */
    public void addCommand(Command command) {
        if (headIndex != currentIndex) {
            for (int i = 0; i < headIndex - currentIndex; i++) {
                history.removeLast();
            }
        }
        history.add(command);
        currentIndex += 1;
        headIndex = currentIndex;
    }
}
