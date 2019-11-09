package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PASSWORDS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;

/**
 * Adds a password to the password book.
 */
public class AddPasswordCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a password to the password book. "
            + "\nParameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORDVALUE + "PASSWORD "
            + "[" + PREFIX_WEBSITE + "WEBSITE] "
            + "[" + PREFIX_TAG + "TAG]";

    public static final String MESSAGE_SUCCESS = "New password added: %1$s";
    public static final String MESSAGE_DUPLICATE_PASSWORD = "This password already exists in the password book.\n"
            + "Please ensure that your password adhere to the following constraints:\n"
            + "1) Should not have the same description and username as those already existing in the password book\n"
            + "2) Description and username are case-insensitive.";

    private final Password toAddPassword;

    public AddPasswordCommand(Password password) {
        requireNonNull(password);
        toAddPassword = password;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPassword(toAddPassword)) {
            throw new CommandException(MESSAGE_DUPLICATE_PASSWORD);
        }

        model.addPassword(toAddPassword);
        model.updateFilteredPasswordList(PREDICATE_SHOW_ALL_PASSWORDS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddPassword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPasswordCommand // instanceof handles nulls
                && toAddPassword.equals(((AddPasswordCommand) other).toAddPassword));
    }
}
