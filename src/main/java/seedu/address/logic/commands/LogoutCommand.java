package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Logs a user out of the incident management system.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_SUCCESS = "Successfully logged out!";

    public static final String MESSAGE_FAILURE = "You are not logged in!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.setSession(null);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
