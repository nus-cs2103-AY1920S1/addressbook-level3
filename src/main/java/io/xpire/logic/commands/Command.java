package io.xpire.logic.commands;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.state.StateManager;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private boolean showInHistory = false;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param stackManager
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model,
                                          StateManager stackManager) throws CommandException, ParseException;

    /**
     * Denotes if the command should be stored in history.
     *
     * @return whether the command should be kept in CommandHistory.
     */
    public boolean isShowInHistory() {
        return this.showInHistory;
    }

    /**
     * Sets showInHistory.
     *
     * @param showInHistory appropriate boolean value for showInHistory.
     */
    public void setShowInHistory(boolean showInHistory) {
        this.showInHistory = showInHistory;
    }
}
