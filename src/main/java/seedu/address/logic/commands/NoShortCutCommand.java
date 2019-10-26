package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents the command entered by the user when he does not want to create a shortcut.
 */
public class NoShortCutCommand extends Command {

    public static final String COMMAND_WORD = "n";
    public static final String SHOWING_NOSHORTCUT_MESSAGE = "No shortcut created. Continue!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_NOSHORTCUT_MESSAGE,
                false, false, false, false, false);
    }
}
