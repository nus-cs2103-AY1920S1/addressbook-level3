package budgetbuddy.logic.commands.transactioncommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;

/**
 * Lists the transactions of the current active account.
 */
public class TransactionListCommand extends Command {

    public static final String COMMAND_WORD = "txn list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all transactions of the current"
            + "active account.\n";

    public static final String MESSAGE_SUCCESS = "Transactions listed from account %1$s.";

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());
        Account activeAccount = model.getAccountsManager().getActiveAccount();
        model.getAccountsManager().transactionListSwitchSource(activeAccount);
        String resultMessage = MESSAGE_SUCCESS;

        return new CommandResult(String.format(resultMessage,
                model.getAccountsManager().getActiveAccount().getName()),
                CommandCategory.TRANSACTION);
    }
}
