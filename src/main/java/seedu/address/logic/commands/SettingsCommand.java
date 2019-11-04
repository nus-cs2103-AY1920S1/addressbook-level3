package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Allows user to view the settings panel.
 */
public class SettingsCommand extends Command {
    public static final String COMMAND_WORD = "settings";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns to settings view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SETTINGS_MESSAGE = "Back to settings panel.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(SHOWING_SETTINGS_MESSAGE, false, false, false);
    }
}
