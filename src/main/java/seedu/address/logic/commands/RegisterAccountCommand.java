package seedu.address.logic.commands;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AccountBook;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.JsonAccountStorage;

import java.io.IOException;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

/**
 * Registers a new account with username and password
 * and saves the account into account storage database.
 */
public class RegisterAccountCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a new account. " + "\n"

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
    // For date, maybe can use this instead --> %tm/%td/%ty

    private final Account toAddAccount;

    public RegisterAccountCommand(Account acc) {
        requireAllNonNull(acc);

        this.toAddAccount = acc;
    }

    public int getNumber() {
        return 5;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AccountStorage accountStorage = new JsonAccountStorage();

        // If earnings with same date and amount and module has already been added.
        try {
            if (accountStorage.getAccountsList().get().hasUsername(toAddAccount.getUsername())) {
                throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountStorage.saveAccount(toAddAccount);
            //model.addAccount(toAddAccount);
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
