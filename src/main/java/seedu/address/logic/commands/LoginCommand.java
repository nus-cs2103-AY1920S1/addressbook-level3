package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;

/**
 * Logs the user in with a username and password provided by user.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log in with your username and password " + "\n"

            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username1 "
            + PREFIX_PASSWORD + "pa5sw0rD";

    public static final String MESSAGE_SUCCESS = "Welcome back!";
    public static final String MESSAGE_FAILURE =
            "Wrong username or password. Please check login details and try again.";

    private final Account account;

    public LoginCommand(Account acc) {
        requireAllNonNull(acc);

        this.account = acc;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, account));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && account.equals(((LoginCommand) other).account));
    }
}
