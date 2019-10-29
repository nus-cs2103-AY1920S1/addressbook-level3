package budgetbuddy.logic.commands.transactioncommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.DATE_EXAMPLE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.rules.RuleProcessor;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.transaction.Transaction;

/**
 * Adds a transaction.
 */
public class TransactionAddCommand extends Command {

    public static final String COMMAND_WORD = "txn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction.\n"
            + "Parameters: "
            + "out|in "
            + PREFIX_AMOUNT + "<amount> "
            + PREFIX_DESCRIPTION + "<description> "
            + "[" + PREFIX_ACCOUNT + "<account>] "
            + "[" + PREFIX_CATEGORY + "<category>] "
            + "[" + PREFIX_DATE + "<date>] "
            + "[" + PREFIX_RECURRENCE + "<d|w|m|y>]\n"
            + "Example: " + COMMAND_WORD + " "
            + "out "
            + PREFIX_AMOUNT + "10 "
            + PREFIX_DESCRIPTION + "apple  "
            + PREFIX_ACCOUNT + "food  "
            + PREFIX_CATEGORY + "fruits "
            + PREFIX_DATE + DATE_EXAMPLE + " "
            + PREFIX_RECURRENCE + "d\n";

    public static final String MESSAGE_SUCCESS = "Transaction added: %1$s";
    public static final String MESSAGE_FAILURE = "Error adding transaction.";

    private final Transaction toAdd;
    private final Account toAccount;

    /**
     * Creates an AddTransactionCommand to add the specified {@code Transaction}
     */
    public TransactionAddCommand(Transaction toAdd, Account toAccount) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.toAccount = toAccount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager());

        Account realToAccount;

        if (toAccount != null) {
            // TODO
            realToAccount = model.getAccountsManager().getAccount(toAccount.getName());
        } else {
            realToAccount = model.getAccountsManager().getActiveAccount();
        }

        if (realToAccount == null) {
            // TODO more specific message
            throw new CommandException(MESSAGE_FAILURE);
        }

        try {
            realToAccount.addTransaction(toAdd);
        } catch (Exception e) {
            //TODO change to accept more specific exception
            throw new CommandException(MESSAGE_FAILURE);
        }
        RuleProcessor.executeRules(model, toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandCategory.TRANSACTION);
    }
}
