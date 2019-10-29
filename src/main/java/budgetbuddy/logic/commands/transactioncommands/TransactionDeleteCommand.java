package budgetbuddy.logic.commands.transactioncommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import budgetbuddy.commons.core.Messages;
import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.transaction.Transaction;


/**
 * Represents the command to delete a transaction.
 */
public class TransactionDeleteCommand extends Command {

    public static final String COMMAND_WORD = "txn delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes transactions with the specified ID"
            + "Parameters: "
            + "<id ..."
            + "Example: " + COMMAND_WORD + " "
            + "5";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public TransactionDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireAllNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactions();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        // TODO SLAP
        model.getAccountsManager().getActiveAccount().deleteTransaction(transactionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete),
                CommandCategory.TRANSACTION);
    }
}
