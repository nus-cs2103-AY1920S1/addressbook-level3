package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.CommandResult;

/**
 * Sorts transactions in the transaction list by their name.
 * Sorts it in alphabetical order of name.
 */
public class SortNameCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel) {
        requireNonNull(model);
        requireNonNull(personModel);
        model.sortByName();
        return new CommandResult(TransactionMessages.MESSAGE_SORTED_BY_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortNameCommand); // instanceof handles nulls
    }
}
