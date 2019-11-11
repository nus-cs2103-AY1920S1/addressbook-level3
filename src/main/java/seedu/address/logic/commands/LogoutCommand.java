package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.UiManager;

/**
 * Logs out the user.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout of your account. " + "\n"

            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Succesfully logged out!";
    public static final String MESSAGE_FAILURE =
            "Unable to logout. Please try again.";


    public LogoutCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.isLoggedOut();
            UiManager.startLoginWindow();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

    }
}
