package budgetbuddy.logic.commands.view;

import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_FROM;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_UNTIL;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.Messages;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.transaction.TransactionMatchesConditionsPredicate;


/**
 * Finds and lists all transactions in AccountsManager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ViewFilterCommand extends Command {

    public static final String COMMAND_WORD = "view filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the main transaction view. If no "
            + "arguments are provided, all transactions are shown. Otherwise, only transactions meeting all "
            + "the criteria are shown.\n"
            + "Parameters: [" + PREFIX_ACCOUNT + "<account>] "
            + "[" + PREFIX_CATEGORY + "<category>] "
            + "[" + PREFIX_FROM + "<from date>] "
            + "[" + PREFIX_UNTIL + "/<to date>]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOUNT + "School "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_FROM + "4/2/2420 "
            + PREFIX_UNTIL + "5/2/2420\n";

    private final TransactionMatchesConditionsPredicate predicate;

    public ViewFilterCommand(TransactionMatchesConditionsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactions().size()), null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewFilterCommand // instanceof handles nulls
                && predicate.equals(((ViewFilterCommand) other).predicate)); // state check
    }
}
