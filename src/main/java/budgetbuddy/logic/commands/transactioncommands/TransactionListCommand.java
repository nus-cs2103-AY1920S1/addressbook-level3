package budgetbuddy.logic.commands.transactioncommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT_FROM;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT_UNTIL;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_FROM;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_UNTIL;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ASCENDING_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ASCENDING_DATE;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ASCENDING_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.SORT_DESCENDING_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_DESCENDING_DATE;
import static budgetbuddy.logic.parser.CliSyntax.SORT_DESCENDING_DESCRIPTION;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_DESCENDING_DATE;

import java.util.Comparator;
import java.util.Optional;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionMatchesConditionsPredicate;

/**
 * Lists the transactions of the current active account.
 */
public class TransactionListCommand extends Command {

    public static final String COMMAND_WORD = "txn list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists transactions from the current"
            + " active account with the option to filter or sort. If no "
            + "arguments are provided, all transactions are shown, sorted by most recent transactions first.\n"
            + "Parameters: "
            + "[" + PREFIX_CATEGORY + "<category>] "
            + "[" + PREFIX_FROM + "<from date>] "
            + "[" + PREFIX_UNTIL + "/<to date>]"
            + "[" + PREFIX_AMOUNT_FROM + "<from amount>] "
            + "[" + PREFIX_AMOUNT_UNTIL + "<until amount>] "
            + "[" + PREFIX_DESCRIPTION + "<description>] "
            + String.format("[%s<%s|%s|%s|%s|%s|%s>]\n", PREFIX_SORT,
            SORT_ASCENDING_DATE, SORT_DESCENDING_DATE,
            SORT_ASCENDING_AMOUNT, SORT_DESCENDING_AMOUNT,
            SORT_ASCENDING_DESCRIPTION, SORT_DESCENDING_DESCRIPTION)
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_FROM + "4/2/2420 "
            + PREFIX_UNTIL + "5/2/2420"
            + PREFIX_AMOUNT_FROM + "20 "
            + PREFIX_AMOUNT_UNTIL + "50 "
            + PREFIX_DESCRIPTION + "lunch "
            + PREFIX_SORT + SORT_ASCENDING_AMOUNT;

    public static final String MESSAGE_SUCCESS = "Transactions listed from account %1$s, "
            + "total nett flow of transactions is %2$s.";

    private final TransactionMatchesConditionsPredicate predicate;
    private Comparator<Transaction> transactionComparator = SORT_BY_DESCENDING_DATE;

    public TransactionListCommand(TransactionMatchesConditionsPredicate predicate,
                                  Optional<Comparator<Transaction>> optionalTransactionComparator) {
        this.predicate = predicate;
        if (optionalTransactionComparator.isPresent()) {
            transactionComparator = optionalTransactionComparator.get();
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getAccountsManager());
        Account activeAccount = model.getAccountsManager().getActiveAccount();
        model.getAccountsManager().transactionListSwitchSource(activeAccount);
        model.getAccountsManager().updateFilteredTransactionList(this.predicate);
        model.getAccountsManager().updateSortedTransactionList(transactionComparator);
        String resultMessage = MESSAGE_SUCCESS;

        return new CommandResult(String.format(resultMessage,
                model.getAccountsManager().getActiveAccount().getName(),
                model.getAccountsManager().getFilteredTransactionListNettFlow().toString()),
                CommandCategory.TRANSACTION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionListCommand // instanceof handles nulls
                && predicate.equals(((TransactionListCommand) other).predicate)
                && transactionComparator.equals(((TransactionListCommand) other).transactionComparator)); // state check
    }
}
