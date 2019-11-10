package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_LAST_ACCOUNT_DELETION_ILLEGAL;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_SINGLE_ID;

import java.util.List;

import budgetbuddy.commons.core.Messages;
import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.account.exceptions.EmptyAccountListException;

/**
 * Delete one or more loans.
 */
public class AccountDeleteCommand extends Command {

    public static final String COMMAND_WORD = "account delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an account.\n"
            + "Parameters: "
            + KEYWORD_SINGLE_ID + " "
            + "Example: " + COMMAND_WORD + " "
            + "3";

    public static final String MESSAGE_DELETE_ACCOUNT_SUCCESS = "Deleted Account: %1$s";

    private final Index targetIndex;

    public AccountDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        AccountsManager accountsManager = model.getAccountsManager();

        List<Account> lastShownList = model.getAccountsManager().getFilteredAccountList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Account accountToDelete = lastShownList.get(targetIndex.getZeroBased());
        try {
            accountsManager.deleteAccount(targetIndex);
        } catch (EmptyAccountListException e) {
            throw new CommandException(MESSAGE_LAST_ACCOUNT_DELETION_ILLEGAL);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_ACCOUNT_SUCCESS, accountToDelete),
                CommandCategory.ACCOUNT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccountDeleteCommand)) {
            return false;
        }

        AccountDeleteCommand otherCommand = (AccountDeleteCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }
}
