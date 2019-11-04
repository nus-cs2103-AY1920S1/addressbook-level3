package seedu.moolah.logic.commands.general;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.model.Model;
import seedu.moolah.ui.panel.PanelName;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help" + CommandGroup.GENERAL;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, PanelName.CURRENT);
    }
}
