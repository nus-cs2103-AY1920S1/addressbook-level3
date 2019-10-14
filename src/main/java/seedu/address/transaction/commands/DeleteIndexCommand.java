package seedu.address.transaction.commands;

import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Deletes a transaction to the transaction list according to the index shown on UI.
 */
public class DeleteIndexCommand extends DeleteCommand {
    private int index;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Transaction} according to index.
     */
    public DeleteIndexCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws ParseException {
        Transaction transaction;
        try {
            transaction = model.findTransactionInFilteredListByIndex(index);
            model.deleteTransaction(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION);
        }
        return new CommandResult(TransactionMessages.deletedTransaction(transaction));
    }
}
