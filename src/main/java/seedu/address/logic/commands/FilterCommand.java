package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.TransactionPredicate;
import seedu.address.ui.tab.Tab;

/**
 * Filters the transactions in the bank account.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = FilterCommand.COMMAND_WORD + ": Filters the transaction "
        + "in the bank account. [Requires at least one parameter.]\n"
        + "Parameters: "
        + "[" + PREFIX_NAME + "DESCRIPTION" + "] "
        + "[" + PREFIX_YEAR + "YEAR" + "] "
        + "[" + PREFIX_MONTH + "MONTH" + "] "
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "milk "
        + PREFIX_MONTH + "11 "
        + PREFIX_YEAR + "2019 "
        + PREFIX_CATEGORY + "drink "
        + PREFIX_CATEGORY + "snack";

    public static final String MESSAGE_SUCCESS = "Bank Account has been filtered!";

    private final TransactionPredicate pred;

    public FilterCommand(TransactionPredicate pred) {
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
                model.getFilteredTransactionList().size()), false, false, Tab.TRANSACTION);
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
