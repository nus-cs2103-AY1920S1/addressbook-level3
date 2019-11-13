package budgetbuddy.logic.commands.accountcommands;

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

/**
 * Generates report of an account.
 */
public class AccountReportCommand extends Command {

    public static final String COMMAND_WORD = "account report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Provides the report of an account.\n"
            + "Parameters: "
            + KEYWORD_SINGLE_ID + " "
            + "Example 1: " + COMMAND_WORD + " 3";

    private final Index targetAccountIndex;

    public AccountReportCommand(Index targetAccountIndex) {
        this.targetAccountIndex = targetAccountIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        AccountsManager accountsManager = model.getAccountsManager();
        accountsManager.resetFilteredAccountList();

        List<Account> lastShownList = model.getAccountsManager().getFilteredAccountList();
        if (targetAccountIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        String accountEssentialInfo = accountsManager.getAccount(targetAccountIndex).getAccountInfo();
        accountsManager.setActiveAccountByIndex(targetAccountIndex);
        return new CommandResult(accountEssentialInfo, CommandCategory.ACCOUNT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AccountReportCommand)) {
            return false;
        }

        AccountReportCommand otherCommand = (AccountReportCommand) other;
        return targetAccountIndex.equals(otherCommand.targetAccountIndex);
    }
}
