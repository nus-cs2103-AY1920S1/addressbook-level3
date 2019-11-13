package budgetbuddy.logic.commands.transactioncommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_ACCOUNT_NOT_FOUND;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.DATE_EXAMPLE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DIRECTION;
import static java.util.Objects.requireNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.scriptcommands.ScriptCommand;
import budgetbuddy.logic.rules.RuleEngine;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;

/**
 * Adds a transaction.
 */
public class TransactionAddCommand extends ScriptCommand {

    public static final String COMMAND_WORD = "txn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction.\n"
            + "Parameters: "
            + PREFIX_DIRECTION + "out|in "
            + PREFIX_AMOUNT + "<amount> "
            + PREFIX_DESCRIPTION + "<description> "
            + "[" + PREFIX_ACCOUNT + "<account>] "
            + "[" + PREFIX_CATEGORY + "<category>] "
            + "[" + PREFIX_DATE + "<date>]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DIRECTION + "out "
            + PREFIX_AMOUNT + "10 "
            + PREFIX_DESCRIPTION + "apple  "
            + PREFIX_ACCOUNT + "food  "
            + PREFIX_CATEGORY + "fruits "
            + PREFIX_DATE + DATE_EXAMPLE + "\n";

    public static final String MESSAGE_SUCCESS = "Transaction added: %1$s";
    public static final String MESSAGE_FAILURE = "Error adding transaction.";

    private final Transaction toAdd;
    private final Name toAccountName;
    private Account toAccount;

    /**
     * Creates an AddTransactionCommand to add the specified {@code Transaction}
     */
    public TransactionAddCommand(Transaction toAdd, Name toAccountName) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.toAccountName = toAccountName;
    }

    @Override
    public CommandResult execute(Model model, ScriptEngine scriptEngine) throws CommandException {
        requireAllNonNull(model, model.getAccountsManager(), scriptEngine);


        if (toAccountName != null) {
            toAccount = model.getAccountsManager().getAccount(toAccountName);
        } else {
            toAccount = model.getAccountsManager().getActiveAccount();
        }

        if (toAccount == null) {
            throw new CommandException(String.format(MESSAGE_ACCOUNT_NOT_FOUND, toAccountName));
        }

        try {
            toAccount.addTransaction(toAdd);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILURE, CommandCategory.TRANSACTION);
        }
        Index txnIndex = Index.fromOneBased(toAccount.getTransactionList().getTransactionsCount());
        RuleEngine.executeRules(model, scriptEngine, txnIndex, toAccount);
        model.getAccountsManager().setActiveAccount(toAccount);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandCategory.TRANSACTION);
    }
}
