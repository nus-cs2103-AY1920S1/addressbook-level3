package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Deletes a transaction to the transaction list.
 */
public class DeleteCommand extends Command {
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
