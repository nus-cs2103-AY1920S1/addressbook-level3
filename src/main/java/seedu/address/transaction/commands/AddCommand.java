package seedu.address.transaction.commands;

import java.util.logging.Logger;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    private Transaction transaction;
    public static final String COMMAND_WORD = "add";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        TransactionMessages transactionMessages = new TransactionMessages();
        model.addTransaction(transaction);
        logger.info(transaction.toString());
        return new CommandResult(transactionMessages.addedTransaction(transaction));
    }
}
