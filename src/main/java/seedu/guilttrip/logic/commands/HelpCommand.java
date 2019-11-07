package seedu.guilttrip.logic.commands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Shows program usage instructions.\n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
