package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.transaction.BankAccountOperation;

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

    private final Comparator<BankAccountOperation> comparator;

    public SortCommand(Comparator<BankAccountOperation> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<BankAccountOperation> sortedTransactionHistory =
            model.getBankAccount().getSortedTransactionHistory(comparator);
        model.setTransactions(sortedTransactionHistory);
        model.commitUserState();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SortCommand) {
            SortCommand sortCommand = (SortCommand) obj;
            return comparator.equals(sortCommand.comparator);
        } else {
            return false;
        }
    }
}
