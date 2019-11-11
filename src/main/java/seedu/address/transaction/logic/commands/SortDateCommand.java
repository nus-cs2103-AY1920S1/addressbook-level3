package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.CommandResult;

/**
 * Sorts transactions in the transaction list by their date.
 * From the oldest date to latest date.
 */
public class SortDateCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel) {
        requireNonNull(model);
        requireNonNull(personModel);
        model.sortByDate();
        return new CommandResult(TransactionMessages.MESSAGE_SORTED_BY_DATE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortDateCommand); // instanceof handles nulls
    }
}
