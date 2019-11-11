package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_ADD_NEGATIVE_TRANSACTION;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_ADD_POSITIVE_TRANSACTION;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.util.CommandResult;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private final Transaction transaction;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCommand(Transaction transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel) {
        model.resetPredicate();
        model.addTransaction(transaction);
        if (transaction.isNegative()) {
            return new CommandResult(String.format(MESSAGE_ADD_NEGATIVE_TRANSACTION, transaction));
        } else {
            return new CommandResult(String.format(MESSAGE_ADD_POSITIVE_TRANSACTION, transaction));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && transaction.equals(((AddCommand) other).transaction));
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
