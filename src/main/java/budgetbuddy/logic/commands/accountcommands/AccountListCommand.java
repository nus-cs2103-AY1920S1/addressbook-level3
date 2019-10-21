package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;


/**
 * Lists accounts.
 */
public class AccountListCommand extends Command {

    public static final String COMMAND_WORD = "account list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all accounts.\n";

    public static final String MESSAGE_SUCCESS = "Listed accounts.";

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());

        StringBuilder builder = new StringBuilder();
        builder.append("Current Accounts:");
        for (int i = 0; i < model.getAccountsManager().getAccountsList().size(); i++) {
            builder.append("\n");
            Account account = model.getAccountsManager().getAccountsList().get(i);
            builder.append(i + 1).append(". ").append(account.getName().toString()).append("\n");
        }

        return new CommandResult(builder.toString());
    }
}
