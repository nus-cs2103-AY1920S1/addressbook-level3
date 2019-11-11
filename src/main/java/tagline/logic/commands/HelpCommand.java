package tagline.logic.commands;

import tagline.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_KEY = "help";

    public static final String MESSAGE_USAGE = COMMAND_KEY + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_KEY;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
