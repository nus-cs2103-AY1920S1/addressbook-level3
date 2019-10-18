package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.LoginCredentialsPredicate;
import seedu.address.model.person.Person;

/**
 * Logs a user into the incident management system with a valid username and password.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log into the incident management system "
            + "with a registered username and password.\n"
            + "Parameters: " + PREFIX_USERNAME + "USERNAME " + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " u/Agent01 w/password";

    private static final String MESSAGE_SUCCESS = "Login Successful!";
    private static final String MESSAGE_FAILURE = "You have entered an invalid username or password.";
    private static final String MESSAGE_MISUSE = "You are already logged in.";

    private final LoginCredentialsPredicate predicate;

    public LoginCommand(LoginCredentialsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        boolean success = model.getFilteredPersonList().size() == 1;
        Person user = model.getFilteredPersonList().get(0);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (!success) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (model.isLoggedIn()) {
            throw new CommandException(MESSAGE_MISUSE);
        }

        model.setSession(user); // Sets session to person remaining in list
        return new CommandResult(MESSAGE_SUCCESS, true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && predicate.equals(((LoginCommand) other).predicate)); // state check
    }
}
