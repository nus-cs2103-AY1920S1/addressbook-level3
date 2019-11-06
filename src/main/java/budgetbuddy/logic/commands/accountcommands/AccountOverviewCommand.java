package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;

/**
 * Exports the overview of all accounts.
 */
public class AccountOverviewCommand extends Command {

    public static final String COMMAND_WORD = "account overview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates the overview of all accounts.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "The overview report of all accounts is successfully generated.\n"
            + "Please navigate to the exports folder and check.";

    public static final String HTML_EXISTS = "The html file has already existed.";

    @Override
    public CommandResult execute(Model model) throws CommandException, IOException {
        requireAllNonNull(model, model.getAccountsManager());
        model.getAccountsManager().resetFilteredAccountList();

        boolean isSuccess = model.getAccountsManager().exportReport();

        if (isSuccess) {
            return new CommandResult(MESSAGE_SUCCESS, CommandCategory.ACCOUNT);
        } else {
            throw new CommandException(HTML_EXISTS);
        }
    }
}
