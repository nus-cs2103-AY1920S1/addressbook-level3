package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.TransactionContainsCategoriesPredicate;

/**
 * Filters the transactions in the bank account.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = FilterCommand.COMMAND_WORD + ": Filters the transaction "
        + "in the bank account.\n"
        + "Parameter: CATEGORY\n"
        + "Example: " + FilterCommand.COMMAND_WORD + " food drink";

    public static final String MESSAGE_SUCCESS = "Bank Account has been filtered!";

    private final TransactionContainsCategoriesPredicate pred;

    public FilterCommand(TransactionContainsCategoriesPredicate pred) {
        requireNonNull(pred);
        this.pred = pred;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTransactionList(pred);
        model.commitUserState();
        return new CommandResult(
            String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof FilterCommand) {
            FilterCommand filterCommand = (FilterCommand) obj;
            return pred.equals(filterCommand.pred);
        } else {
            return false;
        }
    }
}
