package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.exception.DuplicateAccountException;

/**
 * Adds an account.
 */
public class AccountAddCommand extends Command {

    public static final String COMMAND_WORD = "account add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an account.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Japan trip "
            + PREFIX_DESCRIPTION + "Expense spent in Japan.";

    public static final String MESSAGE_SUCCESS = "New account added: %1$s";

    private final Account toAdd;

    public AccountAddCommand(Account account) {
        requireNonNull(account);
        toAdd = account;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        try {
            model.getAccountsManager().addAccount(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandCategory.ACCOUNT);
        } catch (DuplicateAccountException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccountAddCommand)) {
            return false;
        }

        AccountAddCommand otherCommand = (AccountAddCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }
}

