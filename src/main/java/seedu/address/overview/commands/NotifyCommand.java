package seedu.address.overview.commands;

import seedu.address.overview.model.Model;
import seedu.address.overview.model.Transaction;
import seedu.address.overview.ui.TransactionMessages;

/**
 * Deletes a transaction to the transaction list.
 */
public class NotifyCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private int index;

    /**
     * Creates an DeleteCommand to delete the specified {@code Transaction}
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        Transaction transaction = model.findTransactionInFilteredListByIndex(index);
        model.deleteTransaction(index);
        return new CommandResult(TransactionMessages.deletedTransaction(transaction));
    }
}
