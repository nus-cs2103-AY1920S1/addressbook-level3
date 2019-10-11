package seedu.address.transaction.commands;

import java.io.IOException;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private Transaction transaction;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.addTransaction(transaction);
        return new CommandResult(TransactionMessages.addedTransaction(transaction));
    }
}
