package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
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
            + "Some example ...";

    //TODO: NOT TO SHOW THE PASSWORD BUT MAYBE 1 letter + asterixs.
    public static final String MESSAGE_SUCCESS = "New person added: %1";
    private final Password toAddPassword;

    public AddPasswordCommand(Password password) {
        requireNonNull(password);
        toAddPassword = password;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //if (model.hasPerson(toAdd)) {
        //    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        //}

        model.addPassword(toAddPassword);
        return new CommandResult(String.format("Good!", toAddPassword));
    }
}
