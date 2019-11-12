package io.xpire.logic.commands;

import io.xpire.model.Model;
import io.xpire.model.state.StateManager;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_SHORTHAND = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, StateManager stateManager) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

    @Override
    public String toString() {
        return "Help Command";
    }
}

