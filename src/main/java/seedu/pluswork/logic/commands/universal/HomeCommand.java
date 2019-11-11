package seedu.pluswork.logic.commands.universal;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;

/**
 * Allows user to switch view to the dashboard.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns to dashboard view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HOME_MESSAGE = "Back to project dashboard.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(SHOWING_HOME_MESSAGE, false, false, false);
    }
}
