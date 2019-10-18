package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;

/**
 * Adds an account.
 */
public class AddAccountCommand extends Command {

    public static final String COMMAND_WORD = "account";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an account.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Japan trip "
            + PREFIX_DESCRIPTION + "Expense in Japan. ";

    public static final String MESSAGE_SUCCESS = "New loan added: %1$s";

    private final Account toAdd;

    public AddAccountCommand(Account account) {
        requireNonNull(account);
        toAdd = account;
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());

        model.getAccountsManager().addAccount(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddAccountCommand)) {
            return false;
        }

        AddAccountCommand otherCommand = (AddAccountCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }
}
