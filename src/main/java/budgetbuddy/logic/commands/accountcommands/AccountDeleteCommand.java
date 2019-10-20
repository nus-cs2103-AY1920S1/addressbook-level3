package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.exception.AccountNotFoundException;

/**
 * Delete one or more loans.
 */
public class AccountDeleteCommand extends Command {

    public static final String COMMAND_WORD = "account delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes one account.\n"
            + "Parameters: "
            + "<account name>"
            + "...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Japan Trip "
            + "2";

    public static final String MESSAGE_SUCCESS = "Account deleted.";
    public static final String MESSAGE_FAILURE = "No such account found.";

    public final String accountName;

    public AccountDeleteCommand(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        AccountsManager accountsManager = model.getAccountsManager();

        List<Account> lastShownList = accountsManager.getAccountsList();
        for (Account account: lastShownList) {
            try {
                if (account.getName().toString() == accountName) {
                    Account accountToDelete = account;
                    accountsManager.deleteAccount(accountToDelete);
                }
            } catch (AccountNotFoundException e) {
                throw new CommandException(MESSAGE_FAILURE);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountDeleteCommand);
    }
}
