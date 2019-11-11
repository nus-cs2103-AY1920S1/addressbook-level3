package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_NEGATIVE_TRANSACTION;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_POSITIVE_TRANSACTION;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.CommandResult;

/**
 * Deletes a transaction to the transaction list according to the index shown on UI.
 */
public class DeleteIndexCommand extends DeleteCommand {
    private final int index;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Transaction} according to index.
     */
    public DeleteIndexCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model,
                                 CheckAndGetPersonByNameModel personModel)
            throws ParseException {
        requireNonNull(model);
        requireNonNull(personModel);
        Transaction transaction;
        try {
            transaction = model.findTransactionInFilteredListByIndex(index);
            model.deleteTransaction(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION);
        }
        if (transaction.isNegative()) {
            return new CommandResult(String.format(MESSAGE_DELETE_NEGATIVE_TRANSACTION, transaction));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_POSITIVE_TRANSACTION, transaction));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIndexCommand // instanceof handles nulls
                && index == ((DeleteIndexCommand) other).index);
    }
}
