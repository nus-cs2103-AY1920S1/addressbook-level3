package seedu.mark.logic.commands;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.HelpCommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, Storage storage) {
        return new HelpCommandResult(SHOWING_HELP_MESSAGE);
    }
}
