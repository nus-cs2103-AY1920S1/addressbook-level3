package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.authentication.Authentication;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.JsonAccountStorage;

/**
 * Registers a new account with username and password
 * and saves the account into account storage database.
 */
public class RegisterAccountCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Registers a new account. Username and password should not contain any spaces" + "\n"

            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username123 "
            + PREFIX_PASSWORD + "p2s5w0rD";

    public static final String MESSAGE_SUCCESS = "New account created!";
    public static final String MESSAGE_DUPLICATE_ACCOUNT =
            "This username already exists! Please try again";
    public static final String MESSAGE_ERROR = "Unable to register account";

    private final Account toAddAccount;

    public RegisterAccountCommand(Account acc) {
        requireAllNonNull(acc);

        this.toAddAccount = acc;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AccountStorage accountStorage = new JsonAccountStorage();

        try {
            if (accountStorage.getAccountsList().get().hasUsername(toAddAccount.getUsername())) {
                throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            Authentication authenticator = new Authentication();
            String newPassword = authenticator.hashingPassword(toAddAccount.getPassword());
            Account acc = new Account(toAddAccount.getUsername(), newPassword);
            accountStorage.saveAccount(acc);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAddAccount));
        } catch (IOException | DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterAccountCommand // instanceof handles nulls
                && toAddAccount.equals(((RegisterAccountCommand) other).toAddAccount));
    }

}
