package seedu.address.logic.commands.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;


/**
 * Represents the command to delete a transaction.
 */
public class TransactionDeleteCommand extends Command {

    public static final String COMMAND_WORD = "transaction delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes transactions with the specified IDs"
            + "Parameters: "
            + "<id ..."
            + "Example: " + COMMAND_WORD + " "
            + "5 7 13";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public TransactionDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireAllNonNull(model, model.getAccountBook());
        List<Transaction> lastShownList = model.getFilteredTransactions();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete));
    }
}
