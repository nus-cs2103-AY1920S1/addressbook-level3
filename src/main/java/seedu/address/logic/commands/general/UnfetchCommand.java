package seedu.address.logic.commands.general;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Hides the fetch window
 */
public class UnfetchCommand extends Command {

    public static final String COMMAND_WORD = "unfetch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Closes the fetch window (if any).\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_MESSAGE = "Closed fetch window (if any).";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_MESSAGE, false,
                false, null, "unfetch");
    }
}
