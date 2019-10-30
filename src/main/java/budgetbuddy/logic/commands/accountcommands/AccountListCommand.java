package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;


/**
 * Lists accounts.
 */
public class AccountListCommand extends Command {

    public static final String COMMAND_WORD = "account list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all accounts.\n";

    public static final String MESSAGE_SUCCESS = "Accounts listed.";

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());
        model.getAccountsManager().resetFilteredAccountList();

        String resultMessage = MESSAGE_SUCCESS;

        return new CommandResult(resultMessage, CommandCategory.ACCOUNT);
    }
}
