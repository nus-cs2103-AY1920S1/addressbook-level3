package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;

/**
 * Switches the active account.
 */
public class AccountSwitchCommand extends Command {

    public static final String COMMAND_WORD = "account switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the current active account.\n"
            + "Parameters: "
            + "<account number> "
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String MESSAGE_SUCCESS = "Switched to Account %1$s";
    public static final String MESSAGE_FAILURE = "Could not switch to the requested account";

    private final Index targetAccountIndex;

    public AccountSwitchCommand(Index targetAccountIndex) {
        this.targetAccountIndex = targetAccountIndex;
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());
        if (model.getAccountsManager().getFilteredAccountList().size() >= targetAccountIndex.getOneBased()) {
            model.getAccountsManager().setActiveAccount(targetAccountIndex);
        } else {
            return new CommandResult(MESSAGE_INVALID_DISPLAYED_INDEX, CommandCategory.ACCOUNT);
        }
        String resultMessage = String.format(MESSAGE_SUCCESS, model.getAccountsManager().getActiveAccount());

        return new CommandResult(resultMessage, CommandCategory.ACCOUNT);
    }

    private void requireAllNonNull(Model model, AccountsManager accountsManager) {
    }
}
