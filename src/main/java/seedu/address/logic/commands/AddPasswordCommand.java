package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.password.Password;

/**
 * Adds a password to the password book.
 */
public class AddPasswordCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a password to the password book. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORDVALUE + "PASSWORD "
            + PREFIX_TAG + "TAG "
            + "Some example ...";

    public static final String MESSAGE_SUCCESS = "New password added: %1$s";
    public static final String MESSAGE_DUPLICATE_PASSWORD = "This password already exists in the password book";

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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddPassword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPasswordCommand // instanceof handles nulls
                && toAddPassword.equals(((AddPasswordCommand) other).toAddPassword));
    }
}
