package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;
/**
 * Sorts transactions in the transaction list by their date.
 * From the oldest date to latest date.
 */
public class SortDateCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortByDate();
        return new CommandResult(TransactionMessages.MESSAGE_SORTED_BY_DATE);
    }
}
