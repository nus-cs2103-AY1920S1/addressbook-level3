package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = SortCommand.COMMAND_WORD + ": Sorts the transaction "
            + "in the bank account. "
            + "Parameter: BY\n"
            + "Example: " + SortCommand.COMMAND_WORD + " amount";

    public static final String MESSAGE_SUCCESS = "Sorted all transactions";

    private final Comparator<Transaction> comparator;

    public SortCommand(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Transaction> sortedTransactionHistory = model.getBankAccount().getSortedTransactionHistory(comparator);
        model.setTransactions(sortedTransactionHistory);
        model.commitBankAccount();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
