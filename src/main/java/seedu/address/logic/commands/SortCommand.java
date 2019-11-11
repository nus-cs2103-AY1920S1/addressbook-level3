package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.Model;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.ui.tab.Tab;

/**
 * Sorts all transactions in the bank account to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = SortCommand.COMMAND_WORD + ": Sorts the transaction "
        + "in the bank account.\n"
        + "Parameter: PREDICATE/ORDER\n"
        + "Example: " + SortCommand.COMMAND_WORD + " amount/a";

    public static final String MESSAGE_SUCCESS = "Sorted all transactions.";

    private final Comparator<BankAccountOperation> comparator;

    public SortCommand(Comparator<BankAccountOperation> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<BankAccountOperation> transactionHistory = model.getBankAccount().getTransactionHistory();
        ObservableList<BankAccountOperation> sortedTransactionHistory = sortTransactionHistory(transactionHistory);
        model.setTransactions(sortedTransactionHistory);
        model.commitUserState();
        return new CommandResult(MESSAGE_SUCCESS, false, false, Tab.TRANSACTION);
    }

    private SortedList<BankAccountOperation> sortTransactionHistory(ObservableList<BankAccountOperation>
                                                                        transactionHistory) {
        return transactionHistory.sorted(comparator);
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
