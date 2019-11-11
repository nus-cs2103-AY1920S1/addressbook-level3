package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;

/**
 * Returns from a module-view state (from {@code ViewCommand}).
 * Effectively does nothing when not in that state.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Returns from the Module View.\n";

    public static final String MESSAGE_BACK_COMMAND_SUCCESS = "Returned to Home Page.";
    public static final String MESSAGE_BACK_COMMAND_NOT_EFFECTIVE = "Already on Home Page.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getDisplayedModule().isEmpty()) {
            return new CommandResult(MESSAGE_BACK_COMMAND_NOT_EFFECTIVE,
                false, true, false);
        }

        model.setDisplayedModule(null);
        return new CommandResult(MESSAGE_BACK_COMMAND_SUCCESS, false, true, false);
    }
}
