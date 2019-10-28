package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class LogOutCommand extends Command {

    public static final String COMMAND_WORD = "logOut";

    public static final String MESSAGE_SUCCESS = "Logged out successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sign In to your Email Account.";

    public static final String MESSAGE_HAVE_NOT_SIGNED_IN = "User have not signed in. You can proceed to sign in immediately";

    public LogOutCommand(){}

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (!model.isSignedIn()) {
            throw new CommandException(MESSAGE_HAVE_NOT_SIGNED_IN);
        }

        model.logOut();

        return new CommandResult(MESSAGE_SUCCESS, COMMAND_WORD);
    }
}
