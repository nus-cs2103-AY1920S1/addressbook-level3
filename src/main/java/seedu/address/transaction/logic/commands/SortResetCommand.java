package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.CommandResult;

/**
 * Resets transactions in the transaction list to their original ordering when read from the file.
 */
public class SortResetCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel) {
        requireNonNull(model);
        requireNonNull(personModel);
        model.sortReset();
        return new CommandResult(TransactionMessages.MESSAGE_RESET_TO_ORIGINAL_ORDER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortResetCommand); // instanceof handles nulls
    }
}
