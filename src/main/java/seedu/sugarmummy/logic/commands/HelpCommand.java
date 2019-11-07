package seedu.sugarmummy.logic.commands;

import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "I've opened the help window for you~ The "
            + "referenced user guide contains useful information on what I can do and how you may tell me what to do! "
            + "=)";

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.NONE;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
