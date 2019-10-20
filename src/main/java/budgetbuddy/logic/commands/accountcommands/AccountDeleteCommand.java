package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import budgetbuddy.commons.core.Messages;
import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;

/**
 * Delete one or more loans.
 */
public class AccountDeleteCommand extends Command {

    public static final String COMMAND_WORD = "account delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an account.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Japan trip ";

    public static final String MESSAGE_DELETE_ACCOUNT_SUCCESS = "Deleted Account: %1$s";

    private final Index targetIndex;

    public AccountDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        AccountsManager accountsManager = model.getAccountsManager();

        List<Account> lastShownList = model.getAccountsManager().getAccountsList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
        }

        Account accountToDelete = lastShownList.get(targetIndex.getZeroBased());
        accountsManager.deleteAccount(accountToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_ACCOUNT_SUCCESS, accountToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountDeleteCommand);
    }
}
