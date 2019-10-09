package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Sorts transactions in the transaction list by their amount.
 * From the largest amount to smallest amount, in descending amount.
 */
public class SortAmountCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortByAmount();
        return new CommandResult(TransactionMessages.MESSAGE_SORTED_BY_AMOUNT);
    }
}
