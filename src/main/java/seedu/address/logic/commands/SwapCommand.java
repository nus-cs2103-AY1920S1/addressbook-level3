package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Swaps the GUI interface for the user.
 */
public class SwapCommand extends Command {

    public static final String COMMAND_WORD = "swap";

    public static final String MESSAGE_SUCCESS = "Interface swapped!";

    public static final String MESSAGE_FAILURE = "You must login to view incidents and vehicles!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
