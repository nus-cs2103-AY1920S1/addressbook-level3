package seedu.address.logic.commands;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand<T> extends Command<T> {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public CommandResult execute(T model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
