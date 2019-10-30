package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.Messages;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.NameHasKeywordsPredicate;

/**
 * Finds and lists all accounts in accountsManager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AccountFindCommand extends Command {

    public static final String COMMAND_WORD = "account find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all accounts whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " trip";

    private final NameHasKeywordsPredicate predicate;

    public AccountFindCommand(NameHasKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());

        AccountsManager accountsManager = model.getAccountsManager();
        accountsManager.updateFilteredAccountList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ACCOUNTS_LISTED_OVERVIEW,
                        accountsManager.getFilteredAccountList().size()), null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountFindCommand // instanceof handles nulls
                && predicate.equals(((AccountFindCommand) other).predicate)); // state check
    }
}

